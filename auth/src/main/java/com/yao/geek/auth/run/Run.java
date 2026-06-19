package com.yao.geek.auth.run;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动类
 */
@SpringBootApplication(scanBasePackages = "com.yao.geek.auth")
@MapperScan(basePackages = "com.yao.geek.auth") // 自动扫描mapper接口不需要加mapper注解
public class Run {
    public static void main(String[] args) {
        SpringApplication.run(Run.class, args);
    }
}
