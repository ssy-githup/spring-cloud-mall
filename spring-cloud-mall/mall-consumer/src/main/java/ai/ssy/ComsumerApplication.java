package ai.ssy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @ClassName ComsumerApplication
 * @Description: TODO
 * @Author Administrator
 * @Date 2020/3/22
 * @Version V1.0
 **/
@SpringBootApplication
@EnableDiscoveryClient
public class ComsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ComsumerApplication.class, args);
    }
}
