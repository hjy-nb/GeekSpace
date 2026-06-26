package com.yao.geek.common.exception;

import com.yao.geek.common.status.StatusCode;

/**
 * 文章重复发布异常
 */
public class DoublePublishException extends FuException {
    public DoublePublishException(String message) {
        super(message);
    }

    public DoublePublishException(StatusCode code) {
        super(code);
    }
}
