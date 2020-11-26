package ai.ssy.controller;

import ai.ssy.service.TestHystrixService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @ClassName TestHystrix
 * @Description: TODO
 * @Author Administrator
 * @Date 2020/3/22
 * @Version V1.0
 **/
@Controller
@ResponseBody
public class TestHystrix {

    @Autowired
    TestHystrixService testHystrixService;

   @RequestMapping("/testThread")
    public String testThread(){

       return testHystrixService.testThread();
    }

    @RequestMapping("/testCircuitBreaker")
    public String testCircuitBreaker(){
       return testHystrixService.testCircuitBreaker();
    }

    @RequestMapping("/testSemaphore")
    public String testSemaphore(){
        return testHystrixService.testSemaphore();
    }

    @RequestMapping("/testHystrix")
    //@HystrixCommand(fallbackMethod = "fallbackMethod")
    public String testHystrix(){
       int i =1/0;
        return "123";
    }

    public String fallbackMethod(){
       return "降级";
    }

}
