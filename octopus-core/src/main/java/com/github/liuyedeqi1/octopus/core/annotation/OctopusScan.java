package com.github.liuyedeqi1.octopus.core.annotation;

import com.github.liuyedeqi1.octopus.core.config.OctopusImportBeanDefinitionRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 涛声依旧 liuyedeqi@163.com
 * @Description: Octopus主动扫描注解，只有此项配置的包才会被RPC代理
 * @date 2020/4/2116:36
 */
@Target(value = ElementType.TYPE)
@Retention(value = RetentionPolicy.RUNTIME)
@Import(OctopusImportBeanDefinitionRegistrar.class)
public @interface OctopusScan {
    String basePackage() default "";
}
