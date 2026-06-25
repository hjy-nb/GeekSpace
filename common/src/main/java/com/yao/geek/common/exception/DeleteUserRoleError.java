package com.yao.geek.common.exception;

import com.yao.geek.common.status.StatusCode;

/**
 * 删除用户角色错误
 */
public class DeleteUserRoleError extends FuException {
    public DeleteUserRoleError(String message) {
        super(message);
    }

    public DeleteUserRoleError(StatusCode cause) {
        super(cause);
    }
}
