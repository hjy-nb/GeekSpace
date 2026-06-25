package com.yao.geek.auth.run;

import com.yao.geek.common.log.GetLogger;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
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
    private static final Logger B_LOGGER = GetLogger.getBusinessLogger();
    public static void main(String[] args) {
        B_LOGGER.info("auth服务启动");

        SpringApplication.run(AuthRun.class, args);
    }
}
