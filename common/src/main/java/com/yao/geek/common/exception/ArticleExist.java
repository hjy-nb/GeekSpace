package com.yao.geek.common.exception;

import com.yao.geek.common.status.StatusCode;

/**
 * 文章已存在异常
 */
public class ArticleExist extends FuException {
    public ArticleExist(String message) {
        super(message);
    }

    public ArticleExist(StatusCode code) {
        super(code);
    }
}
