package mytest.demo.Controller;

import com.alibaba.fastjson.JSONObject;
import mytest.demo.bean.Series;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;


/**
 * created by suyifei on 2018-07-10
 **/
@Controller
@RequestMapping("/demo")
public class DemoController {
    private static final Logger logger = LoggerFactory
            .getLogger(DemoController.class);

    @RequestMapping("/echartsQuery")
    public String echartsQuery(Model model, String pageNos)
    {
        logger.info("展示页面开始");
        logger.info("展示页面结束");

        return "wsip/echartsShow";
    }

    @RequestMapping("/echartsView")
    @ResponseBody
    public String echartsView()
    {
        List<String> xAxisData = new ArrayList<String>();
        List< JSONObject> seriesList = new ArrayList< JSONObject>();
        for (int i = 1; i < 13; i++)
        {
            xAxisData.add(i+"月");
        }
        for (int i = 1; i < 4; i++)
        {
            List<Integer> list = new ArrayList<Integer>();
            for (int j = 1; j < 13; j++)
            {
                list.add((int)(Math.random()*100));
            }
            Series series = new Series("销售"+i, "TYPE_LINE", list);
            JSONObject job = new JSONObject();
            job.put("name", series.toName());
            job.put("type", "bar");
            job.put("data",series.data);
            seriesList.add(job);
        }
        //xAxisData和seriesList转为json
        JSONObject jsob = new JSONObject();
        jsob.put("xAxisData", xAxisData);
        jsob.put("seriesList", seriesList);
        return jsob.toString();
    }

}
