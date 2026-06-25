package com.yao.geek.common.exception;

import com.yao.geek.common.status.StatusCode;

/**
 * 用户未关注
 */
public class NoAttention extends FuException {
    public NoAttention(String message) {
        super(message);
    }

    public NoAttention(StatusCode cause) {
        super(cause);
    }
}
