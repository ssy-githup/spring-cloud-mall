@[TOC]
# spring-cloud-mall
基于spring-Netflix-cloud开的商城，总结一些典型的案例和开发中遇到的坑
# 环境
idea /  jdk1.8 /  spring Netflix cloud(Finchley-2.0.6) /  mysql5.7 / 
# 1.1 简介
| 项目名称 |项目简介  |
|--|--|
| erureka-server  |  erueka注册中心；  |
|  fegin-api | fegin调用的一个整合包；   |
|  mall-common |  公共模块，pojo |
| mall-gateway  |  gateway网关模块；  |
| mall-consumer  |  消费者  |
| mall-product  |  生产者  |
## 1.2 解决依赖自己common的jar包，但是依赖他的使用maven install 报包找不到错误的问题(启动正常打包却失败)
1. 给自己打jar的加一个打包名称
```csharp
    <build>
        <finalName>mall-common</finalName>
    </build>
```
2. 给父工程增加maven插件： <classifier>execute</classifier>
```csharp
    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <executions>
                    <execution>
                        <phase>none</phase>
                    </execution>
                </executions>
                <configuration>
                    <classifier>execute</classifier>
                </configuration>
            </plugin>
        </plugins>
    </build>
```

# 2. 一个请求是如何流转到数据库的
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200322200836209.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM4MTMwMDk0,size_16,color_FFFFFF,t_70)
请求地址：http://127.0.0.1:8888/test/test/hello/1
1. 首先请求到达gateway由断言(predicates)接收到对应的请求后
2. 使用ribbon对这次请求进行分发到 **MALL-CONSUMER**
3. 其中的一个消费者开始处理这个请求
4. 最后请求根据ribbon的处理到达生产者
# 3. ribbon  fegin  Hystrix  三者组合后调用时的超时时间设置
```java
##===================重试机制===============================


spring.cloud.loadbalancer.retry.enabled=true
##断路器超时时间       这次请求不允许超过的总时间
#断路器超时时间 必须大于 ribbon(1+1+1)*ribbon.ReadTimeout 否则ribbon还没有重试完就被断路器给走降级了
hystrix.command.default.execution.isolation.thread.timeoutInMilliseconds=1000

##请求连接超时时间     请求到提供者返回不能超过的时间
ribbon.ConnectTimeout=200
##请求处理超时时间     请求到提供者后处理的不能超过的时间
ribbon.ReadTimeout=200
##对所有操作请求都进行重试

  #对所有操作请求都进行重试
  #设置为true时，会对所有的请求进行重试，若为false只会对get请求进行重试
  #如果是put或post等写操作，
  #如果服务器接口没做幂等性，会产生不好的结果，所以OkToRetryOnAllOperations慎用。
  #默认情况下,GET方式请求无论是连接异常还是读取异常,都会进行重试
  #非GET方式请求,只有连接异常时,才会进行重试


ribbon.OkToRetryOnAllOperations=true
##切换实例的重试次数
ribbon.MaxAutoRetriesNextServer=2
##对当前实例的重试次数，首次调用不包括在内
ribbon.MaxAutoRetries=1

##==================重试机制===============================
```
# 4. gateway实现动态配置网关
官方教程讲的都是提前在配置文件中配置网关，而在实际工作中不可能随便重启网关服务，这时动态配置就急需要解决；
动态网关配置：redis mysql  gateway2.0.1；
具体代码在mall-gateway模块
