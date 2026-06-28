package com.yao.geek.file.run;

import com.yao.geek.common.log.GetLogger;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 文件服务启动类
 */
@SpringBootApplication(scanBasePackages="com.yao.geek.file")
@MapperScan("com.yao.geek.file.service")
@EnableFeignClients(basePackages = "com.yao.geek.file.common")
public class FileRun {
    private static final Logger B_LOGGER= GetLogger.getBusinessLogger();
    public static void main(String[] args) {
        B_LOGGER.info("file服务启动");

        SpringApplication.run(FileRun.class, args);
    }
}
