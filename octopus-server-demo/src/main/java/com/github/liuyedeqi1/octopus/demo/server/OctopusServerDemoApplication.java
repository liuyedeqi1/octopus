package com.github.liuyedeqi1.octopus.demo.server;

import com.github.liuyedeqi1.octopus.core.annotation.OctopusScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@OctopusScan(basePackage = "com.github.liuyedeqi1.octopus")
public class OctopusServerDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(OctopusServerDemoApplication.class, args);
    }

}
