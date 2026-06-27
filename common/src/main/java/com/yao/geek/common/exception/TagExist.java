package com.yao.geek.common.exception;

import com.yao.geek.common.status.StatusCode;

/**
 * 标签已存在异常
 */
public class TagExist extends FuException {
    public TagExist(String message) {
        super(message);
    }

    public TagExist(StatusCode code) {
        super(code);
    }
}
