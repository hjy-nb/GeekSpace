package com.yao.geek.common.exception;

import com.yao.geek.common.status.StatusCode;

/**
 * 文章内容为空
 */
public class NullArticleContent extends FuException {
    public NullArticleContent(String message) {
        super(message);
    }
    public NullArticleContent(StatusCode code) {
        super(code);
    }
}
