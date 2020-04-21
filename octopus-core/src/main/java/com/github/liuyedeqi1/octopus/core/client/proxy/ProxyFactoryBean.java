package com.github.liuyedeqi1.octopus.core.client.proxy;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * @author 涛声依旧 liuyedeqi@163.com
 * @Description:
 * @date 2020/4/2021:37
 */
public class ProxyFactoryBean implements FactoryBean<Object>, InitializingBean {
    private Class<?> interfaceClass; // 要生成的代理的类型
    private Object proxy;

    @Override
    public Object getObject() throws Exception {
        return proxy;
    }

    @Override
    public Class<?> getObjectType() {
        return interfaceClass;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.proxy = OctopusProxy.clientProxy(interfaceClass);
    }

    public void setInterfaceClass(Class<?> interfaceClass) {
        this.interfaceClass = interfaceClass;
    }
}
