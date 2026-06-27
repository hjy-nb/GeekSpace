package com.yao.geek.common.exception;

import com.yao.geek.common.status.StatusCode;

/**
 * 分类不存在异常
 */
public class CateGoryNotExist extends FuException {
    public CateGoryNotExist(String message) {
        super(message);
    }
    public CateGoryNotExist(StatusCode code) {
        super(code);
    }
}
