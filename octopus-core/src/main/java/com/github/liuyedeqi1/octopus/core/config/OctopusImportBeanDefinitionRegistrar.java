package com.github.liuyedeqi1.octopus.core.config;

import com.github.liuyedeqi1.octopus.core.annotation.OctopusScan;
import com.github.liuyedeqi1.octopus.core.annotation.ReferenceService;
import com.github.liuyedeqi1.octopus.core.client.proxy.ProxyFactoryBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;

/**
 * @author 涛声依旧 liuyedeqi@163.com
 * @Description:
 * @date 2020/4/2116:48
 */
public class OctopusImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

    private Log log = LogFactory.getLog(OctopusImportBeanDefinitionRegistrar.class);

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
        //扫描注解
        Map<String, Object> annotationAttributes = annotationMetadata
                .getAnnotationAttributes(OctopusScan.class.getName());

        if(annotationAttributes.isEmpty()){
            return;
        }

        String basePackages = (String) annotationAttributes.get("basePackage");

        //扫描类
        // 扫描工具类
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter((metadataReader, metadataReaderFactory) -> true); // 设置过滤条件，这里扫描所有
        Set<BeanDefinition> beanDefinitionSet = scanner.findCandidateComponents(basePackages); // 扫描指定路径下的类
        for (BeanDefinition beanDefinition : beanDefinitionSet) {
            String beanClassName = beanDefinition.getBeanClassName(); // 得到class name
            Class<?> beanClass = null;
            try {
                beanClass = Class.forName(beanClassName); // 得到Class对象
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            Field[] fields = beanClass.getDeclaredFields(); // 获得该Class的多有field
            for (Field field : fields) {
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                // @MyReference注解标识
                ReferenceService reference = field.getAnnotation(ReferenceService.class);
                Class<?> fieldClass = field.getType(); // 获取该标识下的类的类型，用于生成相应proxy
                if (reference != null) {
                    //Object referenceObject=OctopusProxy.clientProxy(fieldClass);
                    //MutablePropertyValues mpv = beanDefinition.getPropertyValues();
                    //mpv.addPropertyValue(field.getName(),referenceObject);

                    // 将代理类的beanDefination注册到容器中
                    BeanDefinitionHolder holder = createBeanDefinition(fieldClass);
                    BeanDefinitionReaderUtils.registerBeanDefinition(holder, beanDefinitionRegistry);
                    log.info(beanClass+" "+field.getName()+" proxy was initialized! ");
                }
            }

        }
    }

    /**
     * 生成fieldClass类型的BeanDefinition
     */
    private BeanDefinitionHolder createBeanDefinition(Class<?> fieldClass) {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(ProxyFactoryBean.class);
        String className = fieldClass.getName();
        // bean的name首字母小写，spring通过它来注入
        String beanName = StringUtils.uncapitalize(className.substring(className.lastIndexOf('.')+1));
        // 给ProxyFactoryBean字段赋值
        builder.addPropertyValue("interfaceClass", fieldClass);
        return new BeanDefinitionHolder(builder.getBeanDefinition(), beanName);
    }

}
