package mytest.demo;

import mytest.demo.Mapper.RouterMapperDifCountry;
import mytest.demo.Service.RouterService;
import mytest.demo.bean.TransData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * created by suyifei on 2018-07-02
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class RouterMapperTest {

    @Autowired
    private RouterMapperDifCountry routerMapperDifCountry;
    @Autowired
    private RouterService routerService;

    @Test
    public void insertAll(){
        List<TransData> transDatas = routerService.getTransDatas();

        routerMapperDifCountry.insertAll(transDatas);
    }


}
