package ai.ssy.util.reids;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;


import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Title
 * @Description: RedisTemplate 操作工具类
 * @param:
 */
@Component
public class RedisTemplateHelper {

    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate redisTemplate;


    private static RedisTemplate rt;
    

    @PostConstruct
    public void init() {
        rt = redisTemplate;
        rt.setKeySerializer(new StringRedisSerializer());
        rt.setValueSerializer(new StringRedisSerializer());
        rt.setValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));

    }

    /**
     * @Title saveCache
     * @Description: 缓存
     * @param: key
     * @param: value
     */
    public static void saveCache(String key,Object value){
        rt.opsForValue().set(key,value);
    }
    
    public static void setCache(String key,String value){
    	saveCache(key,value);
    }


    /**
     * @Title saveCache
     * @Description: 缓存
     * @param: key
     * @param: value
     * @param: time  单位：秒
     */
    public static void saveCache(String key,Object value,Long time){
        rt.opsForValue().set(key,value,time, TimeUnit.SECONDS);
    }
    
    public static void setCache(String key,String value,Long time){
    	saveCache(key,value,time);
    }


    /**
     * @Title getCache
     * @Description: 获取缓存
     * @param: key
     */
    public static Object getCache(String key){
        return rt.opsForValue().get(key);
    }
    

    /**
     * @Title removeCache
     * @Description: 删除指定key
     * @param: key
     */
    public static void removeCache(String key){
        rt.delete(key);
    }

    /**
     * 在左侧插入
     * @param key
     * @param strings
     */
    public static void leftPush(String key,Object... strings){
        Long aLong = rt.opsForList().leftPushAll(key, strings);
        System.out.println("aLong="+aLong);
    }
    
    public static List<String> leftPop(String key){
        List<String> o = (List<String>)rt.opsForList().leftPop(key);
        System.out.println("o="+o);
        return o;
    }

    public static void rightPush(String key,String... strings){
        Long aLong = rt.opsForList().rightPushAll(key, strings);
        System.out.println("aLong="+aLong);
    }

    public static List<String> rightPop(String key){
        List<String> o = (List<String>)rt.opsForList().rightPop(key);
        System.out.println("o="+o);
        return o;
    }

    public static Object execute(RedisScript redisScript,List list,Object ...object){
        Object execute = rt.execute(redisScript, list, object);
        return execute;
    }
}
