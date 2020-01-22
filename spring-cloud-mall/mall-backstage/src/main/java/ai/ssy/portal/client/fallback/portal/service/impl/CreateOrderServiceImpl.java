package ai.ssy.portal.client.fallback.portal.service.impl;

import ai.ssy.portal.client.fallback.portal.client.MemberClient;
import ai.ssy.portal.client.fallback.portal.client.OrderClient;
import ai.ssy.portal.client.fallback.portal.client.ProductClient;
import ai.ssy.portal.client.fallback.portal.service.CreateOrderService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateOrderServiceImpl implements CreateOrderService {

    @Autowired
    private ProductClient productClient;
    @Autowired
    private MemberClient memberClient;
    @Autowired
    private OrderClient orderClient;

    @Override
    public JSONObject findThings(String username, String productSn, String orderSn) {
        JSONObject result = new JSONObject();
        String memberStr = memberClient.findByName(username);
        result.put("member", JSONObject.parseObject(memberStr));
        String productStr = productClient.findBySn(productSn);
        result.put("product", JSONObject.parseObject(productStr));
        String orderStr = orderClient.findBySn(orderSn);
        result.put("order", JSONObject.parseObject(orderStr));
        return result;
    }

}
