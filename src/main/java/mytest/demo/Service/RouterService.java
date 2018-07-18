package mytest.demo.Service;

import com.alibaba.fastjson.JSONObject;
import mytest.demo.KAD.LoadRouterInfo;
import mytest.demo.bean.CountryData;
import mytest.demo.bean.NumberRank;
import mytest.demo.bean.TransData;
import mytest.demo.KAD.data.ExtractedRouterInformation;
import mytest.demo.Mapper.RouterMapper;
import mytest.demo.bean.VersionCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * created by suyifei on 2018-07-02
 **/
@Service
public class RouterService {

    @Autowired
    private RouterMapper routerMapper;

    public List<TransData> getTransDatas(){

        LoadRouterInfo load=new LoadRouterInfo();
        List<ExtractedRouterInformation> infos=load.analyseRouterInfo();
        List<TransData> transData = load.transData(infos);

        return transData;
    }

    public int saveDatas(){
        List<TransData> transDatas = getTransDatas();
        int insert = routerMapper.insert(transDatas);
        return insert;
    }

    public void deleteAll() {
        routerMapper.deleteAll();
    }

    public void beforeService(){
        deleteAll();
        saveDatas();
    }

    public  List<CountryData>  countriesCount() {
        List<Map<String ,Object>> transData = routerMapper.countriesCount();
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

    public List<VersionCount> versionCount() {
        List<Map<String,Object>> transData = routerMapper.versionCount();
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
        List<Map<String,Object>> transData = routerMapper.numberRank();
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
}
