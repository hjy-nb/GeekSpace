package com.yao.geek.gateway;

import com.yao.geek.common.log.GetLogger;
import org.slf4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动器类
 */
@SpringBootApplication(scanBasePackages = "com.yao.geek.gateway")
public class GateRun {
    private static final Logger B_LOGGER = GetLogger.getBusinessLogger();
    public static void main(String[] args) {
        B_LOGGER.info("gate服务启动");

        SpringApplication.run(GateRun.class, args);
    }
}
