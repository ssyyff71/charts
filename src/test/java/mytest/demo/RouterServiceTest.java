package mytest.demo;

import mytest.demo.Service.RouterService;
import mytest.demo.bean.CountryData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

/**
 * created by suyifei on 2018-07-02
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class RouterServiceTest {
    @Autowired
    private RouterService routerService;
    @Test
    public void test(){
        int i = routerService.saveDatas();
        System.out.println(i);
    }
    @Test
    public void delete(){
        routerService.deleteAll();
    }

    @Test
    public void country(){
        List<CountryData> data = routerService.countriesCount();
        System.out.println(data);
    }
}
