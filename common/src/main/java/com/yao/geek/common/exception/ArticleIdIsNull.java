package com.yao.geek.common.exception;

import com.yao.geek.common.status.StatusCode;

/**
 * 文章ID为空异常
 */
public class ArticleIdIsNull extends FuException {
    public ArticleIdIsNull(String message) {
        super(message);
    }

    public ArticleIdIsNull(StatusCode code) {
        super(code);
    }
}
