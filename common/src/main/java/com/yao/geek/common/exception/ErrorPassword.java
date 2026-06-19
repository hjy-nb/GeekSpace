package com.yao.geek.common.exception;

import com.yao.geek.common.status.StatusCode;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

/**
 * 密码错误
 */
public class ErrorPassword extends FuException {
    public ErrorPassword(String message) {
        super(message);
    }

    public ErrorPassword(StatusCode cause){
        super(cause);
    }
}
