package com.yao.geek.auth.run;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 启动类
 */
@SpringBootApplication(scanBasePackages = "com.yao.geek.auth")
@MapperScan(basePackages = "com.yao.geek.auth.service")
@EnableFeignClients(basePackages = "com.yao.geek.auth.common")
public class AuthRun {
    public static void main(String[] args) {
        SpringApplication.run(AuthRun.class, args);
    }
}
