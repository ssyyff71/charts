package mytest.demo.Controller;

import com.alibaba.fastjson.JSONObject;
import mytest.demo.Service.RouterService;
import mytest.demo.bean.CountryData;
import mytest.demo.bean.NumberRank;
import mytest.demo.bean.TransData;
import mytest.demo.bean.VersionCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
//        List<TransData> transDatas = routerService.getTransDatas();
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

    @RequestMapping("topCountryForDS500")
    public List<CountryData> country500(){
        List<CountryData> data = routerService.countriesCount500();
        return data;
    }
    @RequestMapping("topCountryForDS1000")
    public List<CountryData> country1000(){
        List<CountryData> data = routerService.countriesCount1000();
        return data;
    }
    @RequestMapping("topCountryForDS5000")
    public List<CountryData> country5000(){
        List<CountryData> data = routerService.countriesCount5000();
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
