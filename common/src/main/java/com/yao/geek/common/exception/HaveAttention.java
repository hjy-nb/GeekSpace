package com.yao.geek.common.exception;

import com.yao.geek.common.status.StatusCode;

/**
 * 用户已关注
 */
public class HaveAttention extends FuException {
    public HaveAttention(String message) {
        super(message);
    }

    public HaveAttention(StatusCode cause) {
        super(cause);
    }
}
