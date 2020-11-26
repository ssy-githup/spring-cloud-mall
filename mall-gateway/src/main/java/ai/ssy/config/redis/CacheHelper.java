package ai.ssy.config.redis;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

/**
 * @Title
 * @Description: redis帮助类
 * @Author:
 * @Date: 2019/10/18 9:37
 */
@Component
public class CacheHelper {

    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate redisTemplate;


    private static RedisTemplate rt;
    
    private static Gson gson = new Gson();

    @PostConstruct
    public void init() {
        rt = redisTemplate;
        rt.setKeySerializer(new StringRedisSerializer());
        rt.setValueSerializer(new StringRedisSerializer());
    }

    /**
     * @Title saveCache
     * @Description: 缓存
     * @param: key
     * @param: value
     void
     * @Throws
     * @Author:
     * @Date: 2019/10/18 9:34
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
     void
     * @Throws
     * @Author:
     * @Date: 2019/10/18 9:34
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
    java.lang.Object
     * @Throws
     * @Author:
     * @Date: 2019/10/18 9:35
     */
    public static Object getCache(String key){
        return rt.opsForValue().get(key);
    }
    
    public static <T> T getCache(final String key,Class<T> clazz) {
		String cache = (String)rt.opsForValue().get(key);
		T t = gson.fromJson(cache,clazz);
		return t;
	}

    /**
     * @Title removeCache
     * @Description: 删除指定key
     * @param: key
     void
     * @Throws
     * @Author:
     * @Date: 2019/10/18 9:35
     */
    public static void removeCache(String key){
        rt.delete(key);
    }




}
