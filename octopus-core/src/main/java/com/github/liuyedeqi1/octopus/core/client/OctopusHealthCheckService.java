package com.github.liuyedeqi1.octopus.core.client;

import com.github.liuyedeqi1.octopus.core.OctopusHeartbeatService;
import com.github.liuyedeqi1.octopus.core.client.proxy.OctopusProxy;
import com.github.liuyedeqi1.octopus.core.utils.Weight;
import com.github.liuyedeqi1.octopus.core.utils.WeightCalculate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author 涛声依旧 liuyedeqi@163.com
 * @Description: Octopus健康检查，定时维护健康的服务列表
 * @date 2020/4/2016:35
 */
@Service
public class OctopusHealthCheckService {

    private ScheduledExecutorService octopusScheduledThreadPool;

    @Autowired
    private OctopusClientProperties octopusClientProperties;

    private Map<String, AvailableHost> healthClientMap = new java.util.concurrent.ConcurrentHashMap<String, AvailableHost>();

    private Map<String, AvailableHost> badClientMap = new java.util.concurrent.ConcurrentHashMap<String, AvailableHost>();

    public void initHealthCheck() {
        if (octopusScheduledThreadPool == null) {
            String hosts[] = octopusClientProperties.getHosts().split(",");
            octopusScheduledThreadPool = Executors.newScheduledThreadPool(hosts.length);
            for (String host : hosts) {
                octopusScheduledThreadPool.scheduleWithFixedDelay(() -> {
                    AvailableHost availableHost=new AvailableHost(host);
                    OctopusHeartbeatService octopusHeartbeatService = OctopusProxy.clientProxy(OctopusHeartbeatService.class, availableHost);
                    String result=octopusHeartbeatService.heartbeat();
                    if (OctopusHeartbeatService.HEART_BEAT_STR.equals(result)) {
                        if(badClientMap.get(host) != null){
                            badClientMap.remove(host);
                        }
                        healthClientMap.put(host, availableHost);
                    } else if (!OctopusHeartbeatService.HEART_BEAT_STR.equals(result)) {
                        if(healthClientMap.get(host) != null){
                            healthClientMap.remove(host);
                        }
                        badClientMap.put(host, availableHost);
                    }
                }, 500, octopusClientProperties.getHeartbeatCycle(), TimeUnit.MILLISECONDS);
                //}, 1000,TimeUnit.MILLISECONDS);
            }
        }
    }

    public AvailableHost getAvailableHost() {
        if(healthClientMap.isEmpty()){
            throw new RuntimeException("There are not have any available clients!");
        }

        List<Weight> weightList = new ArrayList<>();
        for(int i=0;i<healthClientMap.size();i++){
            weightList.add(new Weight(i++,100));
        }
        int index = WeightCalculate.calculate(weightList);

        int i=0;
        for(AvailableHost value:healthClientMap.values()){
            if(i == index){
                return value;
            }
            i++;
        }
        return null;
    }

    @PreDestroy
    public void destory(){
        octopusScheduledThreadPool.shutdown();
    }
}
