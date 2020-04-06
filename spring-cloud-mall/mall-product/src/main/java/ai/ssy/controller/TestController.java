package ai.ssy.controller;


import ai.ssy.entity.MUser;
import ai.ssy.service.MUserService;
import ai.ssy.service.RedpackServiceUtil;
import ai.ssy.util.reids.qhb.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

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

    @Autowired
    public MUserService mUserService;

    @Autowired
    public RedpackServiceUtil redpackServiceUtil;

    @RequestMapping("/hellow/{id}")
    public String queryOrderInfoById(@PathVariable("id") Integer id) {
        int a=1/0;
        System.out.println("请求到达生产者");
        return "hello:"+id;
    }

    /***
    * @FunctionName:
    * @Description: 根据ID查询一个用户
    * @author: ssy
    */
    @RequestMapping("/findMUserById/{id}")
    public MUser queryOrderInfoById(@PathVariable("id") String id) {
        MUser mUserById = mUserService.findMUserById(id);
        return mUserById;
    }
    /*** 
    * @FunctionName:
    * @Description: 到对应的mapper文件查询一个集合
    * @author: ssy
    */
    @RequestMapping("/findMUsers")
    public  List<MUser> findMUsers() {
        List<MUser> mUserById = mUserService.findMUsers();
        return mUserById;
    }

    @RequestMapping("/testCreateHB")
    public String testCreateHB(){
        return redpackServiceUtil.testCreateHB();
    }

    @RequestMapping("/robRedBack")
    public String test11(String userId,String orderId){

        return redpackServiceUtil.robRedBack(userId,orderId);
    }


    private RestTemplate template = new RestTemplate();

    private final int THREAD_NUM=100;
    CountDownLatch cdl = new CountDownLatch(THREAD_NUM);
    //测试抢红包
    @RequestMapping("/testQHB")
    public void testQHB() throws Exception {
        IdWorker idWorker = new IdWorker();
        for (int i = 0; i < THREAD_NUM; i++) {
            int s = 1;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String url = "http://127.0.0.1:8011/myfirst/robRedBack?userId="
                            +idWorker.nextId()+"&orderId="+"hbchiID";
                    String str = template.getForObject(url, String.class);
                    System.out.println(str);
                }
            }).start();
            cdl.countDown();
        }
        TimeUnit.SECONDS.sleep(5);
    }

}
