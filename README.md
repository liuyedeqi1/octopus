# Octopus RPC
Octopus是一个简单易用且轻量级的RPC框架。 <br/>
Octopus无需服务注册中心，其使用了客户端负载均衡的方式来访问RPC服务端，通过定时轮询来检查服务端的可用性。

## Quickstart
maven导入：
```
<dependency>
    <artifactId>octopus</artifactId>
    <groupId>com.github.liuyedeqi1</groupId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```


##  RPC服务端的使用说明：

###  1、首先增加配置参数

服务端的端口<br/>
octopus.server.config.port=18080<br/><br/>

服务端的核心线程数  缺省 -1表示无限制<br/>
octopus.server.config.coreThreadCount=100<br/>

服务端的最大线程数  缺省 同核心线程数<br/>
octopus.server.config.maxThreadCount=100<br/>

服务端的线程回收时间 缺省 60s<br/>
octopus.server.config.threadKeepAliveTime=60<br/><br/>

###  2、包路径扫描配置
在主函数上增加@OctopusScan(basePackage = "your packages")<br/>

###  3、服务对象注解
使用@OctopusService(value=xxxx.class)注解修饰要发布的RPC服务对象<br/>


##  RPC客户端的使用说明：
###  1、首先增加配置参数

远程服务地址，支持多个，格式：127.0.0.1:18080,127.0.0.1:18081 ,多个地址将在客户端作负载均衡调用<br/>
octopus.client.config.hosts=127.0.0.1:18080<br/>

客户端扫描地址<br/>
octopus.client.config.packageScan=com.github.liuyedeqi1.octopus<br/><br/>

客户端健康检查间隔 缺省 1s ，如被检查为不健康服务则会从可用的服务集合中暂时移除，直到下次检查为健康状态才能继续回到可用集合<br/>
octopus.client.config.heartbeatCycleMs=1000<br/>

###  2、客户端注解
使用@Autowired和@ReferenceService(value=xxxx.class)注解修饰要使用的远程服务属性<br/>

如：
```
    @Autowired
    @ReferenceService(value = Xxxxx.class)
    private Xxxxx xxxxx;
```

## 具体的使用方法可以参见：octopus-server-demo和octopus-client-demo
