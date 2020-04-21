package com.github.liuyedeqi1.octopus.core.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 涛声依旧 liuyedeqi@163.com
 * @Description: Octopus的服务注解
 * @date 2020/4/2014:08
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface OctopusService {

    Class<?> value();

    /**
     * 版本号
     */
    String version() default "";
}
