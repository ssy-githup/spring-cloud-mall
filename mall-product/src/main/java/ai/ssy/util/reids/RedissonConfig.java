package ai.ssy.util.reids;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @Title redisson  config
 */
@Configuration
//@PropertySource("classpath:application-redis.properties")
public class RedissonConfig {

    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private String port;

//    @Value("${spring.redis.password}")
//    private String password;

    @Bean
    public RedissonClient redisson()  {
        Config config=new Config();
        System.out.println("==========================================");
        System.out.println("=============实例Redisson=============");
        System.out.println("===================="+"redis://"+host+":"+port+"======================");
        config.useSingleServer().setAddress("redis://"+host+":"+port).setConnectionPoolSize(500);
//        if(password!=null && !"".equals(password.trim())){
//            config.useSingleServer().setPassword(password);
//        }
        return  Redisson.create(config);
    }
}
