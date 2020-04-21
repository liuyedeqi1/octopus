# Octopus RPC
octopus是一个简单易用且轻量级的RPC框架

#maven导入：
<dependency>
    <artifactId>octopus-core</artifactId>
    <groupId>com.github.liuyedeqi1</groupId>
    <version>1.0-SNAPSHOT</version>
</dependency>

#RPC服务端的使用：

1、首先增加配置参数 <br/>

服务端的端口

octopus.server.config.port=18080

服务端的核心线程数  缺省 -1表示无限制

octopus.server.config.coreThreadCount=100

服务端的最大线程数  缺省 同核心线程数

octopus.server.config.maxThreadCount=100

服务端的线程回收时间 缺省 60s

octopus.server.config.threadKeepAliveTime=60

2、使用@OctopusService(value=xxxx.class)注解修饰要发布的RPC服务对象


#RPC客户端的使用：

1、首先增加配置参数<br/>

远程服务地址，支持多个，格式：127.0.0.1:18080,127.0.0.1:18081 ,多个地址将在客户端作负载均衡调用<br/>

octopus.client.config.hosts=127.0.0.1:18080

客户端扫描地址

octopus.client.config.packageScan=com.github.liuyedeqi1.octopus

客户端健康检查间隔 缺省 1s ，如被检查为不健康服务则会从可用的服务集合中暂时移除，直到下次检查为健康状态才能继续回到可用集合

octopus.client.config.heartbeatCycleMs=1000

2、使用@Autowired和@ReferenceService(value=xxxx.class)注解修饰要使用的远程服务属性
如：
    @Autowired
    @ReferenceService(value = Xxxxx.class)
    private Xxxxx xxxxx;
