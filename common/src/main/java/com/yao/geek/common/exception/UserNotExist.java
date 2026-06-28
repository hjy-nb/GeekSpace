package com.yao.geek.common.exception;

import com.yao.geek.common.status.StatusCode;

/**
 * 用户不存在异常
 */
public class UserNotExist extends FuException {
    public UserNotExist(String message) {
        super(message);
    }

    public UserNotExist(StatusCode code) {
        super(code);
    }
}
