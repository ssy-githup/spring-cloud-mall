package ai.ssy.controller;

import ai.ssy.service.MUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName LoginController
 * @Description: TODO
 * @Author Administrator
 * @Date 2020/3/27
 * @Version V1.0
 **/
@Controller
@ResponseBody
public class LoginController {

    @Autowired
    MUserService mUserService;


    @RequestMapping("/login")
    public Map<String,Object> queryOrderInfoById(String userName,
                                                 String passWord) {

        Map<String, Object> login = mUserService.login(userName, passWord);

        System.out.println("请求到达生产者");
        return login;
    }
}
