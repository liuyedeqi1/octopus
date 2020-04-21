package com.github.liuyedeqi1.octopus.core.server;

import com.github.liuyedeqi1.octopus.core.annotation.OctopusService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @author 涛声依旧 liuyedeqi@163.com
 * @Description: Octopus的RPC服务对象，开启核心线程接收请求连接
 * @date 2020/4/2012:57
 */
public class OctopusServer implements InitializingBean, ApplicationContextAware {

    private Log log = LogFactory.getLog(OctopusServer.class);

    @Autowired
    private OctopusServerProperties octopusServerProperties;

    private ExecutorService octopusHandlerService;

    private Map<String, Object> handlerMap = new HashMap();

    private ExecutorService octopusServerExecutor = Executors.newSingleThreadExecutor();

    public static volatile boolean STOP = false;

    @Override
    public void afterPropertiesSet() throws Exception {

        if (octopusServerProperties == null && octopusServerProperties.getPort() == null) {
            return;
        }

        log.info("OctopusServer is initializing ...");

        if (octopusHandlerService == null && octopusServerProperties != null) {
            if (octopusServerProperties.getCoreThreadCount() != null && -1 == octopusServerProperties.getCoreThreadCount()) {
                octopusHandlerService = Executors.newCachedThreadPool();
            } else if (octopusServerProperties.getCoreThreadCount() != null) {
                octopusHandlerService = new ThreadPoolExecutor(octopusServerProperties.getCoreThreadCount(),
                        octopusServerProperties.getMaxThreadCount() == null ? octopusServerProperties.getCoreThreadCount() : octopusServerProperties.getMaxThreadCount(),
                        octopusServerProperties.getThreadKeepAliveTimeout(), TimeUnit.MILLISECONDS,
                        new LinkedBlockingQueue<Runnable>());
            }
        }

        if(octopusServerProperties.getSycnStart()){
            start();
        }else{
            octopusServerExecutor.submit(() -> {
                start();
            });
        }
        log.info("OctopusServer was initialized!");
    }

    private void start(){
        try {
            ServerSocket serverSocket = new ServerSocket(octopusServerProperties.getPort());
            log.info("serverSocket is initializing ,accept on: "+ octopusServerProperties.getPort());
            while (!OctopusServer.STOP) {
                Socket socket = serverSocket.accept();
                octopusHandlerService.submit(new OctopusProcessHandler(socket, handlerMap));
            }
        } catch (IOException e) {
            log.info("serverSocket exception", e);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        log.info("HandlerMap is initializing ...");
        Map<String, Object> beanMap = applicationContext.getBeansWithAnnotation(OctopusService.class);
        if (CollectionUtils.isEmpty(beanMap)) {
            throw new RuntimeException("Not such any RPC service.");
        }
        for (Map.Entry<String, Object> m : beanMap.entrySet()) {
            Object bean = m.getValue();
            OctopusService octopusService = bean.getClass().getAnnotation(OctopusService.class);
            String name = octopusService.value().getName();
            String version = octopusService.version();
            if (!StringUtils.isEmpty(version)) {
                name += "_" + version;
            }
            handlerMap.put(name, bean);
        }
        log.info("HandlerMap was initialized!");
    }

    @PreDestroy
    public void destory() {
        STOP = true;
        octopusHandlerService.shutdown();
        octopusServerExecutor.shutdown();
    }
}
