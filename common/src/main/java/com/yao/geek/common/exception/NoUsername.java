package com.yao.geek.common.exception;

import com.yao.geek.common.status.StatusCode;

/**
 * 用户名不存在
 */
public class NoUsername extends FuException {
    public NoUsername(String message) {
        super(message);
    }

    public NoUsername(StatusCode cause) {
        super(cause);
    }
}
