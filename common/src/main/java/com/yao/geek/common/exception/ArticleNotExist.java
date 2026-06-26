package com.yao.geek.common.exception;

import com.yao.geek.common.status.StatusCode;

/**
 * 文章不存在异常
 */
public class ArticleNotExist extends FuException {
    public ArticleNotExist(String message) {
        super(message);
    }

    public ArticleNotExist(StatusCode code) {
        super(code);
    }
}
