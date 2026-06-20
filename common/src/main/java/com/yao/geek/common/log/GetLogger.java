package com.yao.geek.common.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GetLogger {
    private static final Logger SYSTEM_LOGGER = LoggerFactory.getLogger("SYSTEM");

    private static final Logger BUSINESS_LOGGER = LoggerFactory.getLogger("BUSINESS");

    private static final Logger ERROR_LOGGER = LoggerFactory.getLogger("ERROR");

    public static Logger getSystemLogger() { return SYSTEM_LOGGER;}

    public static Logger getBusinessLogger() {
        return BUSINESS_LOGGER;
    }

    public static Logger getErrorLogger() {
        return ERROR_LOGGER;
    }
}
