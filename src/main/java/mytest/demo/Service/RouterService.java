package mytest.demo.Service;

import mytest.demo.KAD.LoadRouterInfo;
import mytest.demo.bean.TransData;
import mytest.demo.KAD.data.ExtractedRouterInformation;
import mytest.demo.Mapper.RouterMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
