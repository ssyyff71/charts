package mytest.demo.KAD;

import mytest.demo.KAD.data.ExtractedRouterInformation;
import mytest.demo.KAD.data.ObserverProperties;
import mytest.demo.KAD.data.RouterInfoStatistic;
import mytest.demo.bean.TransData;
import net.i2p.data.router.RouterInfo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class LoadRouterInfo {
    private static ObserverProperties observerProperties = new ObserverProperties();
    private static RouterInfoStatistic dailyStatistic;

    public Set<RouterInfo> load() {
        // import the current netDB files
        NetDBImporter importer = new NetDBImporter(observerProperties.getNetDBFolderPath());
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
        NetDBImporter importer = new NetDBImporter(observerProperties.getNetDBFolderPath());
        dailyStatistic = new RouterInfoStatistic(observerProperties.getDailyDate(), false);
        // analyze the imported netDB entries.
        RouterInfoAnalyzer routerInfoAnalyzer = new RouterInfoAnalyzer(observerProperties, importer.readNetDB(),
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
                data.setIP(next.getIpaddresses().get(0));
                data.setKnownLeasesets(next.getKnownRouters());
                data.setKnownLeasesets(next.getKnownLeaseSets());
                data.setRouterVersion(next.getRouterVersion());
                res.add(data);
            }
        }
        return res;
    }

    public static void main(String[] args) {

        LoadRouterInfo loadRouterInfo = new LoadRouterInfo();
        Set<RouterInfo> load = loadRouterInfo.load();
        Iterator<RouterInfo> iterator = load.iterator();
        if (iterator.hasNext()) {
            RouterInfo next = iterator.next();
            long date = next.getDate();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            System.out.println(next);
            System.out.println(sdf.format(date));
        }

    }

}
