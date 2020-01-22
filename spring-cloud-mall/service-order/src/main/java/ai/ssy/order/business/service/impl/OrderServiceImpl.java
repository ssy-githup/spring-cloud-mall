package ai.ssy.order.business.service.impl;

import ai.ssy.order.business.client.MemberClient;
import ai.ssy.order.business.client.ProductClient;
import ai.ssy.order.business.mapper.OrderMapper;
import ai.ssy.order.business.model.Order;
import ai.ssy.order.business.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private MemberClient memberClient;
    @Autowired
    private ProductClient productClient;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Order findBySn(String sn) {
        Order order = orderMapper.findBySn(sn);
        redisTemplate.opsForValue().set("Order::order:" + sn, order);
        return order;
    }

    @Override
    @Cacheable(value = "Order", key = "'order:'+#sn", unless = "#result == null")
    public Order findCacheBySn(String sn) {
        return null;
    }

    @Override
    @Transactional
    public int doSomeThing(String sn, Long productId, Long memberId) {
        int rs1 = orderMapper.updateAmountBySn(sn, new BigDecimal((int) (Math.random() * 100 + 1)));
        int rs2 = memberClient.updatePasswdById(memberId, String.valueOf((int) (Math.random() * 100 + 1)));
        int rs3 = productClient.updatePriceById(productId, new BigDecimal((int) (Math.random() * 100 + 1)));
        if(rs1+rs2+rs3!=3){
            throw new RuntimeException("doSomeThing更新失败");
        }
        return rs1 + rs2 + rs3;
    }

}
