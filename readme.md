# 一 启动步骤

笔记本恢复以后先执行一下项目根目录下的init.sql

1. nacos

   ```shell
   D:\cloud-2023\nacos\bin\startup.cmd -m standalone
   ```

2. sentinel

   就在本项目根目录下, 是一个持久化规则到Nacos的修改版本, 网关规则持久化也做了

   ```shell
   java -jar -Dserver.port=9090 D:\Learning\cloud-2023\sentinel-dashboard.jar
   ```

   http://localhost:9090/

   指定Nacos地址, 启动Dashboard的时候加 -Dnacos.host=localhost:8848

3. seata TC

   ```shell
D:\seata\bin\seata-server.bat
   ```
   
   三个微服务里面的schema.sql已经包含了业务表和AT模式需要的undo_log表了

4. Skywalking

   ```shell
D:\cloud-2023\skywalking\bin\startup.bat
   ```
   
   http://localhost:8080/

5. 微服务接入Skywalking, 需要加入一下JVM 参数

   ```shell
    -javaagent:D:\skywalking\agent\skywalking-agent.jar -Dskywalking.agent.service_name=gateway-service -Dskywalking.collector.backend_service=127.0.0.1:11800 
   ```

## 1.1 微服务启动顺序

在D:\Learning\cloud-2023\portal-service\src\main\resources\templates下, 用命令行启动一个nodejs httpserver

```
http-server -p 80
```

然后就可以通过页面来发起测试了http://localhost/order.html

### 1.1.1 Demo1 -- 演示RestTemplate调用

分支 lb-001

测试URL: POST http://localhost:8082/order/create

参数示例:

```json
{
    "userId": 1001,
    "commodityCode": 1,
    "count": 100,
    "money": 50
}
```

要启动order, account, storage三个服务

Ribbon组件已经被官方弃用, @LoadBalanced注解不生效的问题, 添加loadbalance组件即可解决

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-loadbalancer</artifactId>
</dependency>
```

演示RestTemplate加上@LoadBalanced注解后便具有:

* ribbon客户端负载均衡功能
* 根据微服务名称来调用



### 1.1.2 Demo2 -- 演示RestTemplate服务发现/负载均衡

分支 lb-002

测试URL: POST http://localhost:8082/order/create

参数示例:

```json
{
    "userId": 1001,
    "commodityCode": 1,
    "count": 100,
    "money": 50
}
```

要启动order, account, storage三个服务

Ribbon组件已经被官方弃用, @LoadBalanced注解不生效的问题, 添加loadbalance组件即可解决

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-loadbalancer</artifactId>
</dependency>
```

演示RestTemplate加上@LoadBalanced注解后便具有:

* ribbon客户端负载均衡功能
* 根据微服务名称来调用

1. awesome-service
   * server.port设为0, 就是每次启动分配一个随机端口号, Idea要允许它启动多个instance
   * 这个不需要连数据库

2. portal-service
   * 通过restTemplate.getForObject("http://awesome-service/aws/port", String.class);来循环调用awesome-service 100次, 拿到端口号



### 1.1.2 Demo3 -- 演示feign调用

分支 003-feign

测试URL http://localhost:8082/order/create

要启动order, account, storage三个服务, 然后直接可以打开上面的页面来测试 http://localhost/order.html



### 1.1.3 Nacos 配置中心

分支: 004-nacos-config



1.1.3 Demo3 -- 演示feign 日志

分支 003-feign-logging

测试URL http://localhost:8082/order/create

要启动order, account, storage三个服务, 然后直接可以打开上面的页面来测试 http://localhost/order.html



### 1.1.4 Demo4 -- 演示sentinel dashboard

分支: 005-sentinel-dashboard



### 1.1.5 Demo5 --演示feign拦截器

分支 005-feign-interceptor

account和storage设置了不同的read-timeout时间, 演示不同微服务设置不同的超时时间

测试URL http://localhost:8082/order/create

要启动order, account, storage三个服务, 然后直接可以打开上面的页面来测试 http://localhost/order.html

### 1.1.6 Demo6 -- 演示feign重试机制

分支 006-feign-retry

启动awesome-service, portal-service, 各起一个instance即可

测试URL http://localhost:8081/portal/retry

可以在awesome-service控制台看到输出

```
第1调用了retry
第2调用了retry
第3调用了retry
第4调用了retry
第5调用了retry
第6调用了retry
第7调用了retry
第8调用了retry
```

### 1.1.7 Demo7 -- 演示 httpclient5 连接池

分支 007-httpclient-pool

启动awesome-service 2个instance, portal-service 1个instance

测试URL http://localhost:8081/portal/pool-statistic

通过HttpClientPoolStats打印连接池情况



### 1.1.8 Demo8 -- 演示Nacos配置中心

分支 008-nacos-config

启动portal-service 1个instance

测试URL: http://localhost:8081/portal/info



### 1.1.9 Demo9 -- 演示Seata分布式事务

分支 009-seata

启动order-service, storage-service, account-service

测试URL: http://localhost:8082/order/create

**参数:**

* userId: rico
* commodityCode: 1
* count: 100

### 1.1.10 Sentinel 限流

分支 010-sentinel-dashboard

项目根目录下的sentinel-dashboard.jar已经对FlowRule, ParamFlowRule做了规则持久化到Nacos, 并且测试通过

微服务要在application.yaml添加配置

```yaml
spring:
  cloud:
    sentinel:
      web-context-unify: false
      transport:
        dashboard: localhost:9090
      datasource:
        #流控规则 名称是自己取的, 见名知意就好了
        flow:
          nacos:
            server-addr: localhost:8848
            data-id: ${spring.application.name}-flow-rules
            group-id: SENTINEL_GROUP
            data-type: json
            rule-type: flow
        #降级规则
        degrade:
          nacos:
            server-addr: localhost:8848
            data-id: ${spring.application.name}-degrade-rules
            group-id: SENTINEL_GROUP
            data-type: json
            rule-type: degrade
        paramFlow:
          nacos:
            server-addr: localhost:8848
            data-id: ${spring.application.name}-param-rules
            group-id: SENTINEL_GROUP
            data-type: json
            rule-type: param-flow  
```

portal-service已经添加了热点参数和流控的接口方法了

* 直接流控

  http://localhost:8081/sentinel/direct-flow

* 热点参数

  http://localhost:8081/sentinel/orders

先ApiFox点一次接口, 使得簇点链路可以在sentinel-dashboard出现, 然后添加规则即可
