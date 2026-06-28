package com.yao.geek.comment.run;

import com.yao.geek.common.log.GetLogger;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 启动类
 */
@SpringBootApplication(scanBasePackages = "com.yao.geek.comment")
@MapperScan("com.yao.geek.comment.service")
@EnableFeignClients(basePackages = "com.yao.geek.comment.common")
public class CommentRun {
    private static final Logger B_LOGGER= GetLogger.getBusinessLogger();
    public static void main(String[] args) {
        B_LOGGER.info("comment服务启动");

        SpringApplication.run(CommentRun.class, args);
    }
}
