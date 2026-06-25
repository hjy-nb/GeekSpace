package com.yao.geek.user.run;

import com.yao.geek.common.log.GetLogger;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
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
    private static final Logger B_LOGGER = GetLogger.getBusinessLogger();
    public static void main(String[] args) {
        B_LOGGER.info("user服务启动");

        SpringApplication.run(UserRun.class, args);
    }
}
