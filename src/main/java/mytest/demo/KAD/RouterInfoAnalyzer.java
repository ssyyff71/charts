package mytest.demo.KAD;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.AddressNotFoundException;
import mytest.demo.KAD.data.ExtractedRouterInformation;
import mytest.demo.KAD.data.NetDbEntries;
import mytest.demo.KAD.data.MonitorProperties;
import mytest.demo.KAD.data.RouterInfoStatistic;
import net.i2p.data.router.RouterAddress;
import net.i2p.data.router.RouterInfo;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.*;

public class RouterInfoAnalyzer {

    private MonitorProperties monitorProperties;
    private List<ExtractedRouterInformation> extractedRouterInfo = new ArrayList<>();
    private Set<RouterInfo> netDbEntries;
    private RouterInfoStatistic routerInfoStatistic;
    private boolean processingError;

    public RouterInfoAnalyzer(MonitorProperties monitorProperties, NetDbEntries netDbEntries,
                              RouterInfoStatistic routerInfoStatistic) {
        super();
        this.monitorProperties = monitorProperties;
        this.netDbEntries = netDbEntries.getNetDbEntries();
        this.processingError = netDbEntries.hadProcessingError();
        this.routerInfoStatistic = routerInfoStatistic;
    }

    public void analyzeRouterInfo() {

        convertEntries();
        createStatistics();
    }

    /**
     * 将 RouterInfo 有用的信息抽取封装成 ExtractedRouterInformation
     *
     * @return
     */
    public void convertEntries() {
        int i = 1;
        for (RouterInfo ri : netDbEntries) {

            String hash = ri.getIdentity().getHash().toString().substring(7);

            // 提取IP:PORT

            Collection<RouterAddress> ipaddresses = ri.getAddresses();
            List<String> addresses = new ArrayList<String>();
            List<Integer> ports = new ArrayList<>();
            for (RouterAddress ra : ipaddresses) {
                // 获得有效地址
                if (ra.getHost() != "null" && ra.getPort() != 0) {
                    addresses.add(ra.getHost());
                    ports.add(ra.getPort());
                }
            }

            // 如果该节点属于floodfill，记录它所知的leaseSets，routers个数
            String caps = ri.getOption("caps");
            boolean isFloodfil = false;
            if(caps!=null){
                isFloodfil = ri.getOption("caps").contains("f");
            }
            int flood = 0;
            int knownLeaseSets = 0;
            int knownRouters = 0;
            if (isFloodfil) {
                flood = 1;
                if (ri.getOption("netdb.knownLeaseSets") != null) {
                    knownLeaseSets = Integer.parseInt(ri.getOption("netdb.knownLeaseSets"));
                }
                if (ri.getOption("netdb.knownRouters") != null) {
                    knownRouters = Integer.parseInt(ri.getOption("netdb.knownRouters"));
                }
            }

            // 节点创建时间
            long createTime = ri.getDate();
            // 节点版本号
            String routerVersion = ri.getVersion();

            extractedRouterInfo.add(new ExtractedRouterInformation(hash, addresses, ports, flood, knownLeaseSets,
                    knownRouters, routerVersion, caps, createTime));
            System.out.println("第"+i+"个运转正常");
            i++;
        }

    }

    private void createStatistics() {

        Map<String, Integer> netDbInfo = new TreeMap<String, Integer>();
        netDbInfo.put(monitorProperties.getFLOODFIL_ROUTERS_DENOTATION(), 0);
        netDbInfo.put(monitorProperties.getLEASESETS_DENOTATION(), 0);
        netDbInfo.put(monitorProperties.getKNOWN_ROUTERS_DENOTATION(), 0);
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
                if (eri.isFloodfil() == 1) {
                    netDbInfo.put(monitorProperties.getFLOODFIL_ROUTERS_DENOTATION(),
                            netDbInfo.get(monitorProperties.getFLOODFIL_ROUTERS_DENOTATION()) + 1);
                    netDbInfo.put(monitorProperties.getLEASESETS_DENOTATION(),
                            netDbInfo.get(monitorProperties.getLEASESETS_DENOTATION()) + eri.getKnownLeaseSets());
                    if (eri.getKnownRouters() > netDbInfo.get(monitorProperties.getKNOWN_ROUTERS_DENOTATION())) {
                        netDbInfo.put(monitorProperties.getKNOWN_ROUTERS_DENOTATION(), eri.getKnownRouters());
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

        routerInfoStatistic.addDataset(monitorProperties.getHourlyDate(), netDbEntries.size(), processingError, netDbInfo, versions, countries);

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
        String path = monitorProperties.getGEOIP_DIR() + "/geolite.mmdb";
        File database = new File(path);
        DatabaseReader reader = null;
        reader = new DatabaseReader.Builder(database).build();

        return reader;
    }

    public List<ExtractedRouterInformation> getExtractedRouterInfo() {
        return extractedRouterInfo;
    }
}
