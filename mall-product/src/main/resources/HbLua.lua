---
--- Generated by EmmyLua(https://github.com/EmmyLua)
--- Created by Administrator.
--- DateTime: 2020/4/5 19:27
---
---//查询用户是否已抢过红包，如果用户已抢过红包，则直接返回nil
if redis.call('hexists', KEYS[3], KEYS[4]) ~= 0 then
    --//如果抢过红包 返回“1”
    return '1';
else
    --//从红包池取出一个小红包
    local hb = redis.call('rpop', KEYS[1]);
    -- //判断红包池的红包是否为不空
    if hb then
        local x = cjson.decode(hb);
        --//将红包信息与用户ID信息绑定，表示该用户已抢到红包
        x['userId'] = KEYS[4];
        local re = cjson.encode(x);
        --  //记录用户已抢过 比如 hset hb:rd:{orderId}  {userId}  1
        redis.call('hset', KEYS[3], KEYS[4], '1');
        -- //将抢红包的结果详情存入hb:detailList:{orderId}
        redis.call('lpush', KEYS[2], re);
        return re;
    else
        -- //如果红包已被抢完 返回“0”
        return '0';
    end
end
return nil