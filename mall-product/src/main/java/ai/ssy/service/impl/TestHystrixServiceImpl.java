package ai.ssy.service.impl;

import ai.ssy.service.TestHystrixService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @program: spring-cloud-mall
 * @description: 测试Hystrix
 * @author: ssy
 * @create: 2020-03-23 13:46
 **/
@Service
public class TestHystrixServiceImpl implements TestHystrixService {


    //线程隔离：使用该方式，HystrixCommand将会在单独的线程上执行，并发请求受线程池中线程数量的限制。
    @HystrixCommand(
            groupKey = "ThreadPoolGroupKey",//HystrixCommand 命令所属的组的名称：默认注解方法类的名称
            commandKey = "ThreadPoolCommandKey",//命令的key值，默认值为注解方法的名称
            threadPoolKey = "ThreadPoolKey",//线程池名称，默认定义为groupKey
            fallbackMethod = "fallbackMethod",//定义回退方法的名称, 此方法必须和hystrix的执行方法在相同类中
            // 配置hystrix命令的参数
            commandProperties = {
                    //降级处理超时时间
                    @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value="800"),
                    //该属性用来配置HystrixCommand.run()的执行是否启用超时时间。默认为true
                    @HystrixProperty(name="execution.timeout.enabled", value="true"),
                    //执行的隔离策略。默认为THREAD
                    @HystrixProperty(name="execution.isolation.strategy", value="THREAD")
            }
            , threadPoolProperties = {//核心线程数
            @HystrixProperty(name="coreSize",value="10")
    }
    )
    public String testThread(){
        try {
            int m = new Random().nextInt(1200);
            System.out.println("Thread sleep "+m+" ms");
            //与Thread.sleep();是一样的只是对时间进行了封装(枚举)增加可读性
            TimeUnit.MILLISECONDS.sleep(m);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Thread Pool";
    }

    /**
     * （信号量隔离）：使用该方式，HystrixCommand将会在调用线程上执行，开销相对较小，并发请求受到信号量个数的限制。
     * @return
     */
    @HystrixCommand(
            groupKey = "SemaphoreGroupKey",
            commandKey = "SemaphoreCommandKey",
            threadPoolKey = "SemaphoreThreadPoolKey",
            fallbackMethod = "fallbackMethod",
            commandProperties = {
                    @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value="1000"),
                    @HystrixProperty(name="execution.timeout.enabled", value="true"),
                    @HystrixProperty(name="execution.isolation.strategy", value="SEMAPHORE"),
                    @HystrixProperty(name="execution.isolation.semaphore.maxConcurrentRequests", value="3"),
                    @HystrixProperty(name="fallback.isolation.semaphore.maxConcurrentRequests", value="1")
            }
    )
    public String testSemaphore(){
        try {
            int m = new Random().nextInt(1200);
            System.out.println("Thread sleep "+m+" ms");
            TimeUnit.MILLISECONDS.sleep(m);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Semaphore";
    }

    @HystrixCommand(
            groupKey = "CircuitBreakerGroupKey",
            commandKey = "CircuitBreakerCommandKey",
            threadPoolKey = "CircuitBreakerThreadPoolKey",
            fallbackMethod = "fallbackMethod",
            threadPoolProperties = {
                    @HystrixProperty(name="coreSize",value="10")
            },
            commandProperties = {
                    @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value="1000"),
                    @HystrixProperty(name="execution.timeout.enabled", value="true"),
                    @HystrixProperty(name="circuitBreaker.enabled",value="true"),
                    @HystrixProperty(name="metrics.rollingStats.timeInMilliseconds",value="1000"),
                    @HystrixProperty(name="circuitBreaker.requestVolumeThreshold",value="8"),
                    @HystrixProperty(name="circuitBreaker.errorThresholdPercentage",value="50"),
                    @HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds",value="5000")
            }
    )
    public String testCircuitBreaker(){
        try {
            int value = new Random().nextInt(10);
            System.out.println("Random value "+value);
            if(value % 3 !=0){
                while(true){}
            }else{
                System.out.println("secuss for "+value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "CircuitBreaker";
    }
    public String fallbackMethod(){
        System.out.println("到达简化及");
        return "降级方法";
    }
    public String fallbackMethod(Throwable e){
        System.out.println("到达简化及"+e);
        return "降级方法";
    }
}