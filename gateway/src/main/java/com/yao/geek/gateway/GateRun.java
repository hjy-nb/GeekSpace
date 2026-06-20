package com.yao.geek.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动器类
 */
@SpringBootApplication(scanBasePackages = "com.yao.geek.gateway")
public class GateRun {
    public static void main(String[] args) {
        SpringApplication.run(GateRun.class, args);
    }
}
