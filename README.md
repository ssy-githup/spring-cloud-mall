# spring-cloud-mall
基于spring-Netflix-cloud开的商城，总结一些典型的案例和开发中遇到的坑
# 环境
idea /  jdk1.8 /  spring Netflix cloud(2.0.6) /  mysql5.7
### 1.1 简介
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200122124146239.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM4MTMwMDk0,size_16,color_FFFFFF,t_70)

| 项目名称 |项目简介  |
|--|--|
|  admin-server| 权限管理模块 |
| erureka-server  |  erueka注册中心；  |
|  mall-backstage | 商城后台管理模块；   |
|  mall-front |   商城前端模块； |
| mall-gateway  |  gateway网关模块；  |
| service-member  | 用户(会员)模块；   |
| service-order  | 订单模块；   |
| service-product  |   商品模块； |

# 2.0 gateway实现动态配置网关
官方教程讲的都是提前在配置文件中配置网关，而在实际工作中不可能随便重启网关服务，这时动态配置就急需要解决；
动态网关配置：redis mysql  gateway2.0.1；
具体代码在mall-gateway模块
请参考博客地址;
