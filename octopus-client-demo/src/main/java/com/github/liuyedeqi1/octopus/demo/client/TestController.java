package com.github.liuyedeqi1.octopus.demo.client;

import com.github.liuyedeqi1.octopus.core.annotation.ReferenceService;
import com.github.liuyedeqi1.octopus.demo.server.api.TestRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 涛声依旧 liuyedeqi@163.com
 * @Description:
 * @date 2020/4/2110:55
 */
@RestController
public class TestController {

    @Resource
    @ReferenceService(value = TestRpcService.class)
    private TestRpcService testRpcService;


    @GetMapping("/test")
    public String test() {
        return testRpcService.hello();
    }
}
