package com.github.liuyedeqi1.octopus.core.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author 涛声依旧 liuyedeqi@163.com
 * @Description: Octopus的RPC客户端配置
 * @date 2020/4/2013:15
 */
@Component
public class OctopusClientConfig {

    /**
     * Octopus的RPC服务端hosts,格式：127.0.0.1:8080,127.0.0.1:8081
     */
    @Value("${octopus.client.config.hosts}")
    private String hosts;

    /**
     * Octopus的RPC服务的心跳检查间隔时间
     */
    @Value("${octopus.client.config.heartbeatCycleMs:1000}")
    private Long heartbeatCycle;

    /**
     * 客户端扫描路径
     */
    @Value("${octopus.client.config.packageScan:}")
    private String packageScan;


    public String getHosts() {
        return hosts;
    }

    public void setHosts(String hosts) {
        this.hosts = hosts;
    }


    public Long getHeartbeatCycle() {
        return heartbeatCycle;
    }

    public void setHeartbeatCycle(Long heartbeatCycle) {
        this.heartbeatCycle = heartbeatCycle;
    }

    public String getPackageScan() {
        return packageScan;
    }

    public void setPackageScan(String packageScan) {
        this.packageScan = packageScan;
    }
}
