package ai.ssy.portal.client.fallback.portal.client.fallback;

import ai.ssy.portal.client.fallback.portal.client.OrderClient;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class OrderClientFallback implements OrderClient {

    @Override
    public String findBySn(String sn) {
        System.out.println("OrderClientFallback中的降级方法");
        JSONObject obj = new JSONObject();
        obj.put("id", 9999L);
        obj.put("sn", "Q9999");
        obj.put("payAmount", "999");
        return obj.toJSONString();
    }

}
