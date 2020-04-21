package com.github.liuyedeqi1.octopus.demo;

import com.github.liuyedeqi1.octopus.core.OctopusHeartbeatService;
import com.github.liuyedeqi1.octopus.core.annotation.ReferenceService;
import com.github.liuyedeqi1.octopus.core.client.OctopusProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 涛声依旧 liuyedeqi@163.com
 * @Description:
 * @date 2020/4/2110:55
 */
@RestController
public class TestController {

    @Autowired
    @ReferenceService(value = OctopusHeartbeatService.class)
    private OctopusHeartbeatService octopusHeartbeatService;


    @GetMapping("/test")
    public String test(){
        return octopusHeartbeatService.heartbeat();
    }
}
