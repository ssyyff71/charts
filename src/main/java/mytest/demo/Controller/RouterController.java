package mytest.demo.Controller;

import com.alibaba.fastjson.JSONObject;
import mytest.demo.bean.CountryData;
import mytest.demo.bean.NumberRank;
import mytest.demo.bean.TransData;
import mytest.demo.Service.RouterService;
import mytest.demo.bean.VersionCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * created by suyifei on 2018-07-02
 **/
@RestController
public class RouterController {
    @Autowired
    private RouterService routerService;
    @RequestMapping("router")
    public JSONObject getRouter(){
        JSONObject res=new JSONObject();
        //本地实时路由情况
        List<TransData> transDatas = routerService.getTransDatas();
        //数据库中路由情况
        List<TransData> allRouters = routerService.getAllRouters();
//        res.put("routers",transDatas);
        res.put("routers",allRouters);
        return res;
    }

    @RequestMapping("save")
    public JSONObject save(){
        JSONObject res=new JSONObject();
        int i = routerService.saveDatas();
        res.put("save成功? ",i>1?true:false);
        return res;
    }

    @RequestMapping("clear")
    public String clear(){
         routerService.deleteAll();
         return "清空routerinfo表";
    }

    @RequestMapping("country")
    public List<CountryData> country(){
        List<CountryData> data = routerService.countriesCount();
        return data;
    }

    @RequestMapping("version")
    public List<VersionCount> version(){
        List<VersionCount> versionCounts = routerService.versionCount();
        return versionCounts;
    }
    @RequestMapping("topNumber")
    public List<NumberRank> top(){
        List<NumberRank> numberRanks = routerService.topRank();
        return numberRanks;
    }
}
