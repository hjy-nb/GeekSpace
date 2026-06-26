package com.yao.geek.common.exception;

import com.yao.geek.common.status.StatusCode;

/**
 * 文章不是原创
 */
public class ArticleIsNotOriginal extends FuException {
    public ArticleIsNotOriginal(String message) {
        super(message);
    }

    public ArticleIsNotOriginal(StatusCode statusCode) {
        super(statusCode);
    }
}
