package com.github.liuyedeqi1.octopus.core.client;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @author 涛声依旧 liuyedeqi@163.com
 * @Description: Octopus的RPC客户端对象
 * @date 2020/4/2012:57
 */
public class OctopusClient implements InitializingBean {

    private Log log = LogFactory.getLog(OctopusClient.class);

    @Autowired
    private OctopusClientProperties octopusClientProperties;

    @Autowired
    private OctopusHealthCheckService octopusHealthCheckService;


    @Override
    public void afterPropertiesSet() throws Exception {
        if (octopusClientProperties == null || StringUtils.isEmpty(octopusClientProperties.getHosts())) {
            return;
        }

        log.info("OctopusClient is initializing ...");

        //初始化健康检查任务
        octopusHealthCheckService.initHealthCheck();

        log.info("OctopusClient was initialized!");

    }

}
