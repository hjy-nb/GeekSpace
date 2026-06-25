package com.yao.geek.common.exception;

import com.yao.geek.common.status.StatusCode;

/**
 * 删除用户信息错误
 */
public class DeleteUserDetailError extends FuException {
    public DeleteUserDetailError(String message) {
        super(message);
    }

    public DeleteUserDetailError(StatusCode cause) {
        super(cause);
    }
}
