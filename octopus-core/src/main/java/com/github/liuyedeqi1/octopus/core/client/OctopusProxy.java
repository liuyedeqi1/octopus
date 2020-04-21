package com.github.liuyedeqi1.octopus.core.client;

import com.github.liuyedeqi1.octopus.core.OctopusHeartbeatService;
import org.springframework.stereotype.Component;

import java.lang.reflect.Proxy;

/**
 * @author 涛声依旧 liuyedeqi@163.com
 * @Description: Octopus客户端代理，获取客户端动态代理对象
 * @date 2020/4/2016:06
 */
public class OctopusProxy {
    public static  <T> T clientProxy(final Class<T> interfaceCls, final OctopusHealthCheckService octopusHealthCheckService ){

        return (T) Proxy.newProxyInstance(interfaceCls.getClassLoader(),
                new Class<?>[]{interfaceCls}, new OctopusInvocationHandler(octopusHealthCheckService));
    }

    public static  <T> T clientProxy(final Class<T> interfaceCls, final AvailableHost availableHost){

        return (T) Proxy.newProxyInstance(interfaceCls.getClassLoader(),
                new Class<?>[]{interfaceCls}, new OctopusInvocationHandler(availableHost));
    }

    public static  <T> T clientProxy(final Class<T> interfaceCls){

        return (T) Proxy.newProxyInstance(interfaceCls.getClassLoader(),
                new Class<?>[]{interfaceCls}, new OctopusInvocationHandler());
    }
}
