package ai.ssy.portal.service;

import com.alibaba.fastjson.JSONObject;

public interface CreateOrderService {

    JSONObject findThings(String username, String productSn, String orderSn);

}
