package com.yao.geek.common.exception;

import com.yao.geek.common.status.StatusCode;
import lombok.Builder;
import lombok.Data;

/**
 * 验证码错误
 */
public class ErrorCode extends FuException {
    public ErrorCode(String message) {
        super(message);
    }

    public ErrorCode(StatusCode cause){
        super(cause);
    }
}
