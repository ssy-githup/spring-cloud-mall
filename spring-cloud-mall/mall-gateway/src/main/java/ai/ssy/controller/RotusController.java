package ai.ssy.controller;

import ai.ssy.service.GatewayDefineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController()
@RequestMapping("/gateway")
public class RotusController {

    @Autowired
    GatewayDefineService gatewayDefineService;

    /**
     * @Title reset
     * @Description: 手动刷新路由
     * @param:
     * @Throws
     * @Author: ssy
     * @Date: 2019/10/23 9:57
     */
    @RequestMapping("/reset")
    public String reset(){
        System.out.println("====================刷===========================");
        System.out.println("====================新===========================");
        System.out.println("====================路===========================");
        System.out.println("====================由===========================");
        try {
            gatewayDefineService.reset();
        }catch (Exception e){
            e.printStackTrace();
            return new Date()+ ":更新失败";
        }
        return new Date()+ ":更新成功";
    }
}
