package com.yao.geek.common.exception;

import com.yao.geek.common.status.StatusCode;

/**
 * 分类已存在异常
 */
public class CategoryExist extends FuException {
    public CategoryExist(String message) {
        super(message);
    }
    public CategoryExist(StatusCode code) {
        super(code);
    }
}
