package mytest.demo;

import com.alibaba.fastjson.JSONObject;
import mytest.demo.Controller.RouterController;
import mytest.demo.bean.CountryData;
import mytest.demo.bean.NumberRank;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author sophie
 * @create 2018-11-15 下午8:45
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class RouterControllerTest {
    @Autowired
    private RouterController controller;

    @Test
    public void topNumber(){
        List<NumberRank> top = controller.top();
        top.size();
    }

    @Test
    public void country500(){
        List<CountryData> countryData = controller.country500();
        countryData.size();
    }

    @Test
    public void router(){
        JSONObject router = controller.getRouter();
        router.size();
    }
}
