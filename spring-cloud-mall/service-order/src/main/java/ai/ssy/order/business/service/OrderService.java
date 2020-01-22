package ai.ssy.order.business.service;

import ai.ssy.order.business.model.Order;

public interface OrderService {

    Order findBySn(String sn);

    Order findCacheBySn(String sn);

    int doSomeThing(String sn, Long productId, Long memberId);

}
