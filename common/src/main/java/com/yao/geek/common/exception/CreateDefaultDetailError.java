package com.yao.geek.common.exception;

import com.yao.geek.common.status.StatusCode;

/**
 * 创建默认用户信息异常
 */
public class CreateDefaultDetailError extends FuException {
    public CreateDefaultDetailError(String message) {
        super(message);
    }

    public CreateDefaultDetailError(StatusCode code) {
        super(code);
    }
}
