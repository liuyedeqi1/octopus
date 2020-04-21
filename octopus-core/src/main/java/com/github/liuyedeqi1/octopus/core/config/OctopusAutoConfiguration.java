package com.github.liuyedeqi1.octopus.core.config;

import com.github.liuyedeqi1.octopus.core.client.OctopusClient;
import com.github.liuyedeqi1.octopus.core.client.OctopusClientProperties;
import com.github.liuyedeqi1.octopus.core.server.OctopusServer;
import com.github.liuyedeqi1.octopus.core.server.OctopusServerProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author 涛声依旧 liuyedeqi@163.com
 * @Description: Octopus的自动配置类，用于注册Octopus的客户端和服务端对象
 * @date 2020/4/2115:56
 */
@Configuration
@EnableConfigurationProperties(value={OctopusClientProperties.class,OctopusServerProperties.class})
@ComponentScan(value = "com.github.liuyedeqi1.octopus")
public class OctopusAutoConfiguration {

    @Bean
    @ConditionalOnProperty(name = "octopus.client.config.hosts")
    public OctopusClient octopusClient(){
        return new OctopusClient();
    }

    @Bean
    @ConditionalOnProperty(name = "octopus.server.config.port")
    public OctopusServer octopusServer(){
        return new OctopusServer();
    }
}
