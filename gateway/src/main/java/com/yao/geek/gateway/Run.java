package com.yao.geek.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动器类
 */
@SpringBootApplication(scanBasePackages = "com.yao.geek.gateway")
public class Run {
    public static void main(String[] args) {
        SpringApplication.run(Run.class, args);
    }
}
