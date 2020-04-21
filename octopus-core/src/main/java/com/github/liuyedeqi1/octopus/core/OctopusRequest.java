package com.github.liuyedeqi1.octopus.core;

import java.io.Serializable;

/**
 * @author 涛声依旧 liuyedeqi@163.com
 * @Description: Octopus的RPC数据传输对象
 * @date 2020/4/2014:25
 */
public class OctopusRequest implements Serializable {

    private String beanName;
    private String version;
    private String method;
    private Object[] args;

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }
}
