package ai.ssy.util.reids;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

@Component
public class RedisonHelper {

    @Autowired
    @Qualifier("redisson")
    RedissonClient redissonClient;

    private static RedissonClient rc;

    private static String PREFIX_LOCK = "REDISSON_";

    @PostConstruct
    public void init() {
        rc = redissonClient;
    }

    /**
     * @Title lock
     * @Description: 加锁
     * @param: key
     */
    public static void lock(String key){
        if(ifNull(key)) return;
        RLock rLock = rc.getLock(PREFIX_LOCK+key);
        rLock.lock();
    }

    /**
     * @Title lock
     * @Description: 加锁
     * @param: key
     * @param: time  毫秒
     */
    public static void lock(String key,Long time){
        if(ifNull(key)) return;
        RLock rLock = rc.getLock(PREFIX_LOCK+key);
        rLock.lock(time, TimeUnit.MILLISECONDS);
    }

    /**
     * @Title unlock
     * @Description: 释放锁
     * @param: key
     */
    public static void unlock(String key){
        if(ifNull(key)) return;
        RLock lock = rc.getLock(PREFIX_LOCK+key);
        lock.unlock();
    }

    private static boolean ifNull(String key){
        if(key==null || "".equals(key.trim())){
            return true;
        }
        return false;
    }

}

