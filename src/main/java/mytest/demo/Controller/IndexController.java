package mytest.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * created by suyifei on 2018-07-05
 **/
@Controller
@RequestMapping("/")
public class IndexController {

    public ModelAndView index(){
        return new ModelAndView("router");
    }

}
