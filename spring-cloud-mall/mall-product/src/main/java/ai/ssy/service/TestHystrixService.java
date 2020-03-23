package ai.ssy.service;

/**
 * @program: spring-cloud-mall
 * @description: 测试Hystrix
 * @author: ssy
 * @create: 2020-03-23 13:46
 **/
public interface TestHystrixService {

    public String testThread();

    public String testSemaphore();

    public String testCircuitBreaker();
}