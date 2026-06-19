package com.yao.geek.common.exception;

import com.yao.geek.common.status.StatusCode;
import lombok.Builder;
import lombok.Data;

/**
 * 本项目所有异常的父类
 */

public class FuException extends RuntimeException {
    private String code;

    public FuException(String message) {
        super(message);
    }

    public FuException(StatusCode cause){
        super(cause.getMessage());
        this.code = cause.getCode();
    }

    public String getCode() {
        return code;
    }
}
