package com.github.liuyedeqi1.octopus.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(value = "com.github.liuyedeqi1.octopus")
public class OctopusDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(OctopusDemoApplication.class, args);
    }

}
