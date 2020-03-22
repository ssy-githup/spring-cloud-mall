package ai.ssy.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName TestController
 * @Description: TODO
 * @Author Administrator
 * @Date 2020/3/22
 * @Version V1.0
 **/
@Controller
@ResponseBody
@RequestMapping("/myfirst")
public class TestController {

    @RequestMapping("/hellow/{id}")
    @ResponseBody
    public String queryOrderInfoById(@PathVariable("id") Integer id) {
        System.out.println("请求到达生产者");
        return "hello:"+id;
    }
}
