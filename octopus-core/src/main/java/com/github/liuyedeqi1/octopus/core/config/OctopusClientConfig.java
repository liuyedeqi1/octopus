package com.github.liuyedeqi1.octopus.core.config;

import com.github.liuyedeqi1.octopus.core.client.OctopusClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 涛声依旧 liuyedeqi@163.com
 * @Description:
 * @date 2020/4/2117:04
 */

public class OctopusClientConfig {

    @Bean
    public OctopusClient octopusClient(){
        return new OctopusClient();
    }
}
