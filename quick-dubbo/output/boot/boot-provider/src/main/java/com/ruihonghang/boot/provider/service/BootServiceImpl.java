package com.ruihonghang.boot.provider.service;
import com.alibaba.dubbo.config.annotation.Service;
import com.ruihonghang.api.service.DemoService;
@Service(version = "${demo.service.version}")
public class BootServiceImpl implements DemoService {
    @Override
    public String sayHello(String name) {
        return "hello i am "+name;
    }
}

