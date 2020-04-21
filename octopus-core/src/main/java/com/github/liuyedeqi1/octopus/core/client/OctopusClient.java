package com.github.liuyedeqi1.octopus.core.client;

import com.github.liuyedeqi1.octopus.core.annotation.ReferenceService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import java.lang.reflect.Field;
import java.util.Set;

/**
 * @author 涛声依旧 liuyedeqi@163.com
 * @Description: Octopus的RPC客户端对象
 * @date 2020/4/2012:57
 */
@Component
public class OctopusClient implements InitializingBean {

    private Log log = LogFactory.getLog(OctopusClient.class);

    @Autowired
    private OctopusClientConfig octopusClientConfig;

    @Autowired
    private OctopusHealthCheckService octopusHealthCheckService;


    @Override
    public void afterPropertiesSet() throws Exception {
        if (octopusClientConfig == null && StringUtils.isEmpty(octopusClientConfig.getHosts())) {
            return;
        }

        log.info("OctopusClient is initializing ...");

        //初始化健康检查任务
        octopusHealthCheckService.initHealthCheck();

        //扫描ReferenceService将被此注解注释的属性bean赋值由Octopus代理的对象
        scanReferenceBean();

        log.info("OctopusClient was initialized!");

    }

    private void scanReferenceBean() {
        // 扫描工具类
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter((metadataReader, metadataReaderFactory) -> true); // 设置过滤条件，这里扫描所有
        Set<BeanDefinition> beanDefinitionSet = scanner.findCandidateComponents(octopusClientConfig.getPackageScan()); // 扫描指定路径下的类
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
                    Object referenceObject = OctopusProxy.clientProxy(fieldClass);
                    MutablePropertyValues mpv = beanDefinition.getPropertyValues();
                    try {
                        mpv.addPropertyValue(field.getName(), referenceObject);
                        log.info(beanClass+" "+field.getName()+" proxy was initialized! ");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }

}
