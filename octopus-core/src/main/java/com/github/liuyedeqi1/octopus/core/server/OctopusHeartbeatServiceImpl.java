package com.github.liuyedeqi1.octopus.core.server;

import com.github.liuyedeqi1.octopus.core.OctopusHeartbeatService;
import com.github.liuyedeqi1.octopus.core.annotation.OctopusService;
import org.springframework.stereotype.Service;

/**
 * @author 涛声依旧 liuyedeqi@163.com
 * @Description: Octopus 心跳服务
 * @date 2020/4/2015:14
 */
@Service
@OctopusService(value = OctopusHeartbeatService.class)
public class OctopusHeartbeatServiceImpl implements OctopusHeartbeatService {

    public String heartbeat() {
        return HEART_BEAT_STR;
    }
}
