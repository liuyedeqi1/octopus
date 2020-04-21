package com.github.liuyedeqi1.octopus.core.client;

/**
 * @author 涛声依旧 liuyedeqi@163.com
 * @Description:
 * @date 2020/4/2020:47
 */
public class AvailableHost {
    private String host;
    private Integer port;

    public AvailableHost(String host) {
        String[] hosts=host.split(":");
        this.host = hosts[0];
        this.port = Integer.valueOf(hosts[1]);
    }

    public String getHost() {
        return host;
    }

    public Integer getPort() {
        return port;
    }
}
