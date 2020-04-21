package com.github.liuyedeqi1.octopus.demo.server;

import com.github.liuyedeqi1.octopus.core.annotation.OctopusService;
import com.github.liuyedeqi1.octopus.demo.server.api.TestRpcService;

/**
 * @author 涛声依旧 liuyedeqi@163.com
 * @Description:
 * @date 2020/4/2115:27
 */
@OctopusService(TestRpcService.class)
public class TestRpcServiceImpl implements TestRpcService{

    @Override
    public String hello() {
        return "hello world!";
    }
}
