package com.yao.geek.common.exception;

import com.yao.geek.common.status.StatusCode;

/**
 * 标签不存在异常
 */
public class TagNotExist extends FuException {
    public TagNotExist(String message) {
        super(message);
    }

    public TagNotExist(StatusCode code) {
        super(code);
    }
}
