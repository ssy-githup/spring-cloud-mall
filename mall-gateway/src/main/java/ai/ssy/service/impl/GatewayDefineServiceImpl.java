package ai.ssy.service.impl;

import ai.ssy.config.redis.CacheHelper;
import ai.ssy.mapper.GatewayDefineMapper;
import ai.ssy.service.GatewayDefineService;
import ai.ssy.vo.GatewayDefine;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GatewayDefineServiceImpl implements GatewayDefineService {

    @Autowired
    GatewayDefineMapper gatewayDefineMapper;

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * @Title reset
     * @Description: 更新redis缓存，同时触发监听，更新路由缓存
     * @Date: 2019/10/23 9:40
     */
    public void reset() throws Exception{
        List<GatewayDefine> gatewayDefineServiceAll = gatewayDefineMapper.findAll();
        CacheHelper.saveCache("gateway_rotus", JSON.toJSONString(gatewayDefineServiceAll));
        this.publisher.publishEvent(new RefreshRoutesEvent(this));
    }


}
