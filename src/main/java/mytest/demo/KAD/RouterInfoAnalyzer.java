package mytest.demo.KAD;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.AddressNotFoundException;
import mytest.demo.KAD.data.ExtractedRouterInformation;
import mytest.demo.KAD.data.NetDbEntries;
import mytest.demo.KAD.data.ObserverProperties;
import mytest.demo.KAD.data.RouterInfoStatistic;
import net.i2p.data.router.RouterAddress;
import net.i2p.data.router.RouterInfo;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.*;

public class RouterInfoAnalyzer {

    private ObserverProperties observerProperties;
    private List<ExtractedRouterInformation> extractedRouterInfo = new ArrayList<>();
    private Set<RouterInfo> netDbEntries;
    private RouterInfoStatistic routerInfoStatistic;
    private boolean processingError;

    public RouterInfoAnalyzer(ObserverProperties observerProperties, NetDbEntries netDbEntries,
                              RouterInfoStatistic routerInfoStatistic) {
        super();
        this.observerProperties = observerProperties;
        this.netDbEntries = netDbEntries.getNetDbEntries();
        this.processingError = netDbEntries.hadProcessingError();
        this.routerInfoStatistic = routerInfoStatistic;
    }

    public void analyzeRouterInfo() {

        convertEntries();
        createStatistics();
    }

    /**
     * Converts RouterInfo into {@link ExtractedRouterInformation}
     *
     * @return List with {@link ExtractedRouterInformation}
     */
    public void convertEntries() {

        for (RouterInfo ri : netDbEntries) {

            // format hash value into String, cut "Hash: " from output of
            // toString()
            String hash = ri.getIdentity().getHash().toString().substring(7);

            // extract all addresses of the router in form IP:Port
            Collection<RouterAddress> ipaddresses = ri.getAddresses();
            List<String> addresses = new ArrayList<String>();
            for (RouterAddress ra : ipaddresses) {
                // don't add invalid addresses
                if (ra.getHost() != "null") {
                    addresses.add(ra.getHost());
                }
            }

            // check, if router is a floodfil router and extract amount of
            // leaseSets and routers
            String caps = ri.getOption("caps");
            boolean isFloodfil = ri.getOption("caps").contains("f");
            int flood=0;
            int knownLeaseSets = 0;
            int knownRouters = 0;
            if (isFloodfil) {
                flood=1;
                // filter "null" entries (meaning no known leaseSets)
                if (ri.getOption("netdb.knownLeaseSets") != null) {
                    knownLeaseSets = Integer.parseInt(ri.getOption("netdb.knownLeaseSets"));
                }
            }
            if (isFloodfil) {
                // filter "null" entries (meaning no known router)
                if (ri.getOption("netdb.knownRouters") != null) {
                    knownRouters = Integer.parseInt(ri.getOption("netdb.knownRouters"));
                }
            }
            // collect router's createdTime
            // collect software version of router
            long createTime = ri.getDate();
            String routerVersion = ri.getVersion();

            // create new object of ExtractedRouterInformation with collected
            // information
            extractedRouterInfo.add(new ExtractedRouterInformation(hash, addresses, flood, knownLeaseSets,
                    knownRouters, routerVersion,caps,createTime));
        }

    }

    private void createStatistics() {

        Map<String, Integer> netDbInfo = new TreeMap<String, Integer>();
        netDbInfo.put(observerProperties.getFLOODFIL_ROUTERS_DENOTATION(), 0);
        netDbInfo.put(observerProperties.getLEASESETS_DENOTATION(), 0);
        netDbInfo.put(observerProperties.getKNOWN_ROUTERS_DENOTATION(), 0);
        Map<String, Integer> versions = new TreeMap<String, Integer>();
        Map<String, Integer> countries = new TreeMap<String, Integer>();

        DatabaseReader reader = null;
        try {
            reader = initalizeGeoIpDatabase();
        } catch (Exception ex) {
            System.err.println("Failed to initialize GeoIP database. Skipping translation from IP to country.");
            processingError = true;
        }
        InetAddress ipAddress = null;

        int unknownAddresses = 0;

        if (reader != null) {
            int numberOfErrors = 0;

            for (ExtractedRouterInformation eri : extractedRouterInfo) {

                // count objects of floodfil routers
                if (eri.isFloodfil()==1) {
                    netDbInfo.put(observerProperties.getFLOODFIL_ROUTERS_DENOTATION(),
                            netDbInfo.get(observerProperties.getFLOODFIL_ROUTERS_DENOTATION()) + 1);
                    netDbInfo.put(observerProperties.getLEASESETS_DENOTATION(),
                            netDbInfo.get(observerProperties.getLEASESETS_DENOTATION()) + eri.getKnownLeaseSets());
                    if (eri.getKnownRouters() > netDbInfo.get(observerProperties.getKNOWN_ROUTERS_DENOTATION())) {
                        netDbInfo.put(observerProperties.getKNOWN_ROUTERS_DENOTATION(), eri.getKnownRouters());
                    }
                }

                // count version numbers
                String version = eri.getRouterVersion();
                if (versions.containsKey(version)) {
                    versions.put(version, versions.get(version) + 1);
                } else {
                    versions.put(version, 1);
                }

                // count IPs per country
                if (!eri.getIpaddresses().isEmpty()) {
                    String address = eri.getIpaddresses().get(0);
                    String country = "none";
                    try {
                        ipAddress = InetAddress.getByName(address);
                        country = reader.country(ipAddress).getCountry().getName();
                        // filter invalid responses (null) from GeoIP
                        if (country != null) {
                            if (countries.containsKey(country)) {
                                countries.put(country, countries.get(country) + 1);
                            } else {
                                countries.put(country, 1);
                            }
                        } else {
                            unknownAddresses++;
                        }
                    } catch (Exception ex) {
                        unknownAddresses++;

                        // filter GeoIP exceptions for invalid addresses (i.e.
                        // 127.0.0.1)
                        if (!ex.getClass().equals(AddressNotFoundException.class)) {
                            numberOfErrors++;
                        }
                    }
                } else {
                    unknownAddresses++;
                }
            }
            if (numberOfErrors >= 20) {
                processingError = true;
            }
        }

        countries.put("Unknown", unknownAddresses);

        routerInfoStatistic.addDataset(observerProperties.getHourlyDate(), netDbEntries.size(), processingError, netDbInfo, versions, countries);

    }

    public String getCountry(ExtractedRouterInformation info) {

        DatabaseReader reader = null;
        try {
            reader = initalizeGeoIpDatabase();
        } catch (Exception ex) {
            System.err.println("Failed to initialize GeoIP database. Skipping translation from IP to country.");
            processingError = true;
        }
        String country = "none";
        InetAddress ipAddress = null;
        if (!info.getIpaddresses().isEmpty()) {
            String address = info.getIpaddresses().get(0);
            try {
                ipAddress = InetAddress.getByName(address);
                country = reader.country(ipAddress).getCountry().getName();
            } catch (Exception e) {
                System.err.println("Failed to parse the IP to country.");
                processingError = true;
            }
        }
        return country;

    }

    private DatabaseReader initalizeGeoIpDatabase() throws IOException {
        String path = observerProperties.getGEOIP_DIR() + "/geolite.mmdb";
        File database = new File(path);
        DatabaseReader reader = null;
        reader = new DatabaseReader.Builder(database).build();

        return reader;
    }

    public List<ExtractedRouterInformation> getExtractedRouterInfo() {
        return extractedRouterInfo;
    }
}
