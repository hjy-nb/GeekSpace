package com.yao.geek.common.exception;

import com.yao.geek.common.status.StatusCode;

/**
 * 删除用户关注错误
 */
public class DeleteUserAttentionError extends FuException {
    public DeleteUserAttentionError(String message) {
        super(message);
    }

    public DeleteUserAttentionError(StatusCode cause) {
        super(cause);
    }
}
