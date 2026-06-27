package com.yao.geek.common.exception;

import com.yao.geek.common.status.StatusCode;

/**
 * 标签ID为空异常
 */
public class TagIdIsNull extends FuException {
    public TagIdIsNull(String message) {
        super(message);
    }

    public TagIdIsNull(StatusCode code) {
        super(code);
    }
}
