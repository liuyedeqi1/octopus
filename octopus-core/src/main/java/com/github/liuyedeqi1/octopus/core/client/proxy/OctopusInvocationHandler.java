package com.github.liuyedeqi1.octopus.core.client.proxy;

import com.github.liuyedeqi1.octopus.core.OctopusRequest;
import com.github.liuyedeqi1.octopus.core.client.AvailableHost;
import com.github.liuyedeqi1.octopus.core.client.OctopusHealthCheckService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author 涛声依旧 liuyedeqi@163.com
 * @Description: Octopus客户段代理handler
 * @date 2020/4/2016:09
 */
@Component
public class OctopusInvocationHandler implements InvocationHandler,ApplicationContextAware {

    private AvailableHost availableHost;
    private OctopusHealthCheckService octopusHealthCheckService;
    private static ApplicationContext applicationContext;

    public OctopusInvocationHandler() {
    }

    public OctopusInvocationHandler(OctopusHealthCheckService octopusHealthCheckService) {
        this.octopusHealthCheckService = octopusHealthCheckService;
    }

    public OctopusInvocationHandler(AvailableHost availableHost) {
        this.availableHost = availableHost;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //请求数据的包装
        //ReferenceService octopusService = proxy.getClass().getAnnotation(ReferenceService.class);
        OctopusRequest request = new OctopusRequest();
        request.setBeanName(method.getDeclaringClass().getName());
        request.setMethod(method.getName());
        request.setArgs(args);
        //request.setVersion(octopusService.version());

        boolean allowMethod = false;
        Method[] methods=method.getDeclaringClass().getMethods();
        for(Method m:methods){
            if (m.getName().equals(method.getName())) {
                allowMethod = true;
                break;
            }
        }

        if(!allowMethod){
            throw new RuntimeException("not remote method："+method.getName());
        }

        //远程通信
        if (availableHost == null) {
            if(octopusHealthCheckService == null){
                initOctopusHealthCheckService();
            }
            //负载均衡获取一个可用的服务
            AvailableHost availableHost = octopusHealthCheckService.getAvailableHost();
            OctopusClientConnector connector = new OctopusClientConnector(availableHost.getHost(), availableHost.getPort());
            Object result = connector.call(request);
            return result;
        } else {
            OctopusClientConnector connector = new OctopusClientConnector(availableHost.getHost(), availableHost.getPort());
            Object result = connector.call(request);
            return result;
        }

    }

    private synchronized void initOctopusHealthCheckService(){
        if(octopusHealthCheckService == null){
            octopusHealthCheckService = applicationContext.getBean(OctopusHealthCheckService.class);
            if (octopusHealthCheckService == null) {
                throw new RuntimeException("OctopusHealthCheckService is not initializer！");
            }
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;
    }
}
