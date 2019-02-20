package mytest.demo.KAD;

import mytest.demo.KAD.data.ExtractedRouterInformation;
import mytest.demo.KAD.data.MonitorProperties;
import mytest.demo.KAD.data.RouterInfoStatistic;
import mytest.demo.bean.TransData;
import net.i2p.data.DataFormatException;
import net.i2p.data.router.RouterInfo;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class LoadRouterInfo {
    private static MonitorProperties monitorProperties = new MonitorProperties();
    private static RouterInfoStatistic dailyStatistic;

    public Set<RouterInfo> load() {
        // import the current netDB files
        NetDBImporter importer = new NetDBImporter(monitorProperties.getNetDBFolderPath());
        Set<RouterInfo> netDbEntries = importer.readNetDB().getNetDbEntries();
        return netDbEntries;
    }

    public List<ExtractedRouterInformation> analyseRouterInfo() {
        RouterInfoAnalyzer routerInfoAnalyzer = getAnalyzer();
        routerInfoAnalyzer.convertEntries();
        List<ExtractedRouterInformation> extractedRouterInfo = routerInfoAnalyzer.getExtractedRouterInfo();
        return extractedRouterInfo;
    }

    public RouterInfoAnalyzer getAnalyzer(){
        NetDBImporter importer = new NetDBImporter(monitorProperties.getNetDBFolderPath());
        // analyze the imported netDB entries.
        RouterInfoAnalyzer routerInfoAnalyzer = new RouterInfoAnalyzer(monitorProperties, importer.readNetDB(),
                dailyStatistic);
        return routerInfoAnalyzer;
    }

    public List<TransData> transData(List<ExtractedRouterInformation> from) {
        List<TransData> res=new ArrayList<>();
        if (!from.isEmpty()) {
            Iterator<ExtractedRouterInformation> iterator = from.iterator();
            RouterInfoAnalyzer analyzer=getAnalyzer();
            while (iterator.hasNext()) {
                TransData data=new TransData();
                ExtractedRouterInformation next = iterator.next();
                data.setHash(next.getHash());
                data.setFloodfill(next.isFloodfil());
                data.setCaps(next.getCaps());
                data.setCountry( analyzer.getCountry(next));
                data.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(next.getCreateTime()));
                if(next.getIpaddresses().size()>0){
                    data.setIP(next.getIpaddresses().get(0));
                }
                if(next.getPorts().size()>0){
                    data.setPort(next.getPorts().get(0));
                }
                data.setKnownLeasesets(next.getKnownLeaseSets());
                data.setKnownRouters(next.getKnownRouters());
                data.setRouterVersion(next.getRouterVersion());
                res.add(data);
            }
        }
        return res;
    }

    public static RouterInfo getOneRouterInfo(String filePath){
        File file=new File(filePath);
        RouterInfo router = new RouterInfo();
        try (BufferedInputStream input = new BufferedInputStream(new FileInputStream(file))) {
            router.readBytes(input);
        } catch (DataFormatException | IOException ex) {
            if (ex.getClass().equals(FileNotFoundException.class)) {
            } else {
                System.err.println("Failed to load one netDB entry.");
                ex.printStackTrace();
            }
        }
        return router;
    }
}
