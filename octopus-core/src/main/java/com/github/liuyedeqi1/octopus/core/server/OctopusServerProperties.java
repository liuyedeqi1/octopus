package com.github.liuyedeqi1.octopus.core.server;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author 涛声依旧 liuyedeqi@163.com
 * @Description: Octopus的RPC服务配置
 * @date 2020/4/2013:15
 */
@ConfigurationProperties(prefix = "octopus.server" )
public class OctopusServerProperties {

    @Value("${octopus.server.config.port:}")
    private Integer port;

    @Value("${octopus.server.config.coreThreadCount:-1}")
    private Integer coreThreadCount;

    @Value("${octopus.server.config.maxThreadCount:}")
    private Integer maxThreadCount;

    @Value("${octopus.server.config.threadKeepAliveTime:60}")
    private Integer threadKeepAliveTimeout;

    @Value("${octopus.server.config.sync-start:false}")
    private Boolean sycnStart;


    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Integer getCoreThreadCount() {
        return coreThreadCount;
    }

    public void setCoreThreadCount(Integer coreThreadCount) {
        this.coreThreadCount = coreThreadCount;
    }

    public Integer getMaxThreadCount() {
        return maxThreadCount;
    }

    public void setMaxThreadCount(Integer maxThreadCount) {
        this.maxThreadCount = maxThreadCount;
    }

    public Integer getThreadKeepAliveTimeout() {
        return threadKeepAliveTimeout;
    }

    public void setThreadKeepAliveTimeout(Integer threadKeepAliveTimeout) {
        this.threadKeepAliveTimeout = threadKeepAliveTimeout;
    }

    public Boolean getSycnStart() {
        return sycnStart;
    }

    public void setSycnStart(Boolean sycnStart) {
        this.sycnStart = sycnStart;
    }
}
