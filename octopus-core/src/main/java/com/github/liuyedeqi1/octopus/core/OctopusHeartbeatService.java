package com.github.liuyedeqi1.octopus.core;

import com.github.liuyedeqi1.octopus.core.annotation.OctopusService;
import org.springframework.stereotype.Service;

/**
 * @author 涛声依旧 liuyedeqi@163.com
 * @Description: Octopus 心跳服务
 * @date 2020/4/2015:14
 */
public interface OctopusHeartbeatService {

    String HEART_BEAT_STR = "OK";
    /**
     * 心跳检查
     */
    String heartbeat();
}
