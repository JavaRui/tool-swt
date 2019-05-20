package com.${groupId}.${artifactId}.provider.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.${groupId}.api.service.DemoService;

@Service(version = "${demo.service.version}")
public class ${ArtifactId}ServiceImpl implements DemoService {
    @Override
    public String sayHello(String name) {
        return "hello i am "+name;
    }
}
