package com.${groupId}.${artifactId}.consumer;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 *
 */
@EnableDubbo
@SpringBootApplication
public class ${ArtifactId}ConsumerApplication
{
    public static void main(String[] args) {
        SpringApplication.run(${ArtifactId}ConsumerApplication.class, args);
    }
}
