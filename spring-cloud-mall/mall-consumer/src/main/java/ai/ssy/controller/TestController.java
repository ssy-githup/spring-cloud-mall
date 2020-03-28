package ai.ssy.controller;

import ai.ssy.feginapi.LoginFeginApi;
import ai.ssy.feginapi.TestFeginApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @ClassName TestController
 * @Description: TODO
 * @Author Administrator
 * @Date 2020/3/22
 * @Version V1.0
 **/
@Controller

public class TestController {

    @Autowired
    private TestFeginApi testFeginApi;

    @Autowired
    private LoginFeginApi loginFeginApi;

    @RequestMapping("/login")
    @ResponseBody
    public Map<String,Object> queryOrderInfoById(String userName,
                                                 String passWord) {

        return loginFeginApi.login(userName,passWord);

    }

    @RequestMapping("/test/hellow/{id}")
    @ResponseBody
    public String queryOrderInfoById(@PathVariable("id") Integer id) {
        System.out.println("请求到达消费者");
        return "hello:"+id;

    }


    @RequestMapping("/test/hello/{id}")
    @ResponseBody
    public String testFegin(@PathVariable("id")Integer id){
        System.out.println("11111111");
        return testFeginApi.queryById(id);
    }

    @RequestMapping("/test")
    @ResponseBody
    public String sss(){
        System.out.println("11111111");
        return "1111";
    }
}
