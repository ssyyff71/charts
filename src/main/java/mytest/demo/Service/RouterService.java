package mytest.demo.Service;

import com.alibaba.fastjson.JSONObject;
import mytest.demo.KAD.LoadRouterInfo;
import mytest.demo.bean.CountryData;
import mytest.demo.bean.TransData;
import mytest.demo.KAD.data.ExtractedRouterInformation;
import mytest.demo.Mapper.RouterMapper;
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

    public int saveOne(){
        List<TransData> transDatas = getTransDatas();
        TransData transData = transDatas.get(0);
        int i = routerMapper.insertOne(transData);
        return i;

    }

    public void deleteAll() {
        routerMapper.deleteAll();
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

}
