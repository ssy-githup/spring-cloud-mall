package ai.ssy.portal.client.fallback.portal.client.fallback;

import ai.ssy.portal.client.fallback.portal.client.ProductClient;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class ProductClientFallback implements ProductClient {

    @Override
    public String findBySn(String sn) {
        System.out.println("ProductClientFallback中的降级方法");
        JSONObject obj = new JSONObject();
        obj.put("id", 9999L);
        obj.put("sn", "SN9999");
        obj.put("price", "999");
        return obj.toJSONString();
    }

}
