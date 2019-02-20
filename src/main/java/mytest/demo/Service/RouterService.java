package mytest.demo.Service;

import mytest.demo.KAD.LoadRouterInfo;
import mytest.demo.KAD.data.ExtractedRouterInformation;
import mytest.demo.Mapper.RouterMapper;
import mytest.demo.Mapper.RouterMapper1000;
import mytest.demo.Mapper.RouterMapper5000;
import mytest.demo.bean.CountryData;
import mytest.demo.bean.NumberRank;
import mytest.demo.bean.TransData;
import mytest.demo.bean.VersionCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * created by suyifei on 2018-07-02
 **/
@Service
public class RouterService {

    @Autowired
    private RouterMapper routerMapper;

    @Autowired
    private RouterMapper1000 routerMapper1000;

    @Autowired
    private RouterMapper5000 routerMapper5000;

    public List<TransData> getTransDatas(){

        LoadRouterInfo load=new LoadRouterInfo();
        List<ExtractedRouterInformation> infos=load.analyseRouterInfo();
        List<TransData> transData = load.transData(infos);

        return transData;
    }

    public int saveDatas(){
        List<TransData> transDatas = getTransDatas();
        int insert = routerMapper.insert500(transDatas);
        return insert;
    }

    public void deleteAll() {
        routerMapper.deleteAll500();
    }

    public void beforeService(){
        deleteAll();
        saveDatas();
    }

    public  List<CountryData>  countriesCount500() {
        List<Map<String ,Object>> transData = routerMapper.countriesCount500();
        return transDatatoCountryData(transData);
    }

    public  List<CountryData>  countriesCount1000() {
        List<Map<String ,Object>> transData = routerMapper1000.countriesCount1000();
        return transDatatoCountryData(transData);
    }

    public  List<CountryData>  countriesCount5000() {
        List<Map<String ,Object>> transData = routerMapper5000.countriesCount5000();
        return transDatatoCountryData(transData);
    }

    public List<VersionCount> versionCount() {
        List<Map<String,Object>> transData = routerMapper.versionCount500();
        List<VersionCount> list=new ArrayList<>();
        for(int i=0;i<transData.size();i++){
            Object version = transData.get(i).get("router_version");
            Object count = transData.get(i).get("count");
            int number = Integer.parseInt(count.toString());
            if(version!=null&&count!=null){
                VersionCount ver=new VersionCount(version.toString(),number);
                list.add(ver);
            }
        }
        return list;
    }


    public List<NumberRank> topRank() {
        List<Map<String,Object>> transData = routerMapper.numberRank500();
        List<NumberRank> list=new ArrayList<>();
        for(int i=0;i<transData.size();i++){
            Object routers=transData.get(i).get("known_routers");
            int number = Integer.parseInt(routers.toString());
            Object country = transData.get(i).get("country");
            NumberRank rank=new NumberRank(country.toString(),number);
            list.add(rank);
        }
        return list;
    }

    public List<TransData> getAllRouters() {
        List<TransData> transData = routerMapper.selectAll500();
        return transData;
    }

    public List<CountryData> transDatatoCountryData(List<Map<String ,Object>> transData){
        List<CountryData> datas=new ArrayList<>();
        for(int i=0;i<transData.size();i++){
            Object country = transData.get(i).get("country");
            Object count = transData.get(i).get("count");
            int number = Integer.parseInt(count.toString());
            CountryData data=new CountryData(country.toString(),number);
            datas.add(data);
        }
        return datas;
    }
}
