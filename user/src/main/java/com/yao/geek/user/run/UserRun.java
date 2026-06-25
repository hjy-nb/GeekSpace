package com.yao.geek.user.run;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

/**
 * 启动类
 */
@SpringBootApplication(scanBasePackages = "com.yao.geek.user")
@MapperScan(basePackages = "com.yao.geek.user.service")
@EnableFeignClients(basePackages = "com.yao.geek.user.common")
public class UserRun {
    public static void main(String[] args) {
        SpringApplication.run(UserRun.class, args);
    }
}
