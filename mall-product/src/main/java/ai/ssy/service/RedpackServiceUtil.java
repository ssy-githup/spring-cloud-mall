package ai.ssy.service;

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
public interface RedpackServiceUtil {

    public String testCreateHB();

    public String robRedBack(String userId,String orderId);
}
