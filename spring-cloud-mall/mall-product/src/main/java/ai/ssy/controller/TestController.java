package ai.ssy.controller;


import ai.ssy.entity.MUser;
import ai.ssy.service.MUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

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
}
