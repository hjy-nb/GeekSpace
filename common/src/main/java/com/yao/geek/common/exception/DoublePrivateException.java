package com.yao.geek.common.exception;

import com.yao.geek.common.status.StatusCode;

/**
 * 文章重复设为私密
 */
public class DoublePrivateException extends FuException {
    public DoublePrivateException(String message) {
        super(message);
    }

    public DoublePrivateException(StatusCode code) {
        super(code);
    }
}
