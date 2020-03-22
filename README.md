# spring-cloud-mall
基于spring-Netflix-cloud开的商城，总结一些典型的案例和开发中遇到的坑
# 环境
idea /  jdk1.8 /  spring Netflix cloud(Finchley-2.0.6) /  mysql5.7 / 
### 1.1 简介
| 项目名称 |项目简介  |
|--|--|
| erureka-server  |  erueka注册中心；  |
|  fegin-api | fegin调用的一个整合包；   |
|  mall-common |  公共模块，pojo |
| mall-gateway  |  gateway网关模块；  |
| mall-consumer  |  消费者  |
| mall-product  |  生产者  |
# 2一个请求是如何流转到数据库的
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200322200836209.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM4MTMwMDk0,size_16,color_FFFFFF,t_70)
请求地址：http://127.0.0.1:8888/test/test/hello/1
1. 首先请求到达gateway由断言(predicates)接收到对应的请求后
2. 使用ribbon对这次请求进行分发到 **MALL-CONSUMER**
3. 其中的一个消费者开始处理这个请求
4. 最后请求根据ribbon的处理到达生产者

# 3.0 gateway实现动态配置网关
官方教程讲的都是提前在配置文件中配置网关，而在实际工作中不可能随便重启网关服务，这时动态配置就急需要解决；
动态网关配置：redis mysql  gateway2.0.1；
具体代码在mall-gateway模块
