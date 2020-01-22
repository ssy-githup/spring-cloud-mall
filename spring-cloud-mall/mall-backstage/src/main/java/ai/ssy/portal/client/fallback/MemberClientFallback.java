package ai.ssy.portal.client.fallback;

import ai.ssy.portal.client.MemberClient;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class MemberClientFallback implements MemberClient {

    @Override
    public String findByName(String username) {
        System.out.println("MemberClientFallback中的降级方法");
        JSONObject obj = new JSONObject();
        obj.put("id", 9999L);
        obj.put("username", "admin");
        obj.put("password", "admin");
        return obj.toJSONString();
    }

}
