package com.com.ruihonghang.boot.consumer.controller;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.rpc.RpcContext;
import com.com.ruihonghang.api.service.DemoService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class BootController {
    @Reference(version = "${demo.service.version}")
    private DemoService demoService;
    @RequestMapping("/sayHello/{name}")
    public String sayHello(@PathVariable("name") String name) {
       s
        System.out.println("name---->"+name);
        return demoService.sayHello(name);
    }
}

