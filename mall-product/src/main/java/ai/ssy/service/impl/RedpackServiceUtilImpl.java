package ai.ssy.service.impl;

import ai.ssy.service.RedpackServiceUtil;
import ai.ssy.util.reids.RedisTemplateHelper;
import ai.ssy.util.reids.qhb.RedpackService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName RedpackServiceUtil
 * @Description: TODO
 * @Author Administrator
 * @Date 2020/4/5
 * @Version V1.0
 **/
@Service
public class RedpackServiceUtilImpl implements RedpackServiceUtil {


    @Autowired
    RedpackService redpackService;

    /**
     * 在红包池中生成5个测试红包
     */
    public String testCreateHB(){

//        for (int i = 1; i<=5; i++){
//            JSONObject object = new JSONObject();
//            object.put("id", i); //红包ID
//            object.put("money", i);   //红包金额
//            RedisTemplateHelper.leftPush("hb:pool:123456",object);
//        }
//        return "生成测试红包成功orderId = hb:pool:123456";

        /**
         * @param orderId 红包池的ID
         * @param totalAmount 红包的总钱数
         * @param  redPackCount 生成红包的个数
         *
         */
        redpackService.genRedpack("hbchiID",5,20000);
        return "生成生成环境红包成功orderId = hbchiID";
        //redpackService.genRedpack("111",20);
    }

    public String robRedBack(String userId,String orderId){

        String object = redpackService.snatchRedpack(userId, orderId);
        String string = null;
        if ("0".equals(object)){
            string = "hb result:" + object + "已领完";
        }else if ("1".equals(object)){
            string = "hb result:" + object + "已领取";
        }else{
            string = object;
        }

        return string;
    }
}
