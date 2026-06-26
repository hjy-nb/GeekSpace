package com.yao.geek.blog.run;

import com.yao.geek.common.log.GetLogger;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 启动类
 */
@SpringBootApplication(scanBasePackages = "com.yao.geek.blog")
@MapperScan("com.yao.geek.blog.service")
@EnableFeignClients(basePackages = "com.yao.geek.blog.common")
public class BlogRun {
    private static final Logger B_LOGGER = GetLogger.getBusinessLogger();
    public static void main(String[] args) {
        B_LOGGER.info("blog服务启动");

        SpringApplication.run(BlogRun.class, args);
    }
}
