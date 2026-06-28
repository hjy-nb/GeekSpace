package com.yao.geek.common.exception;

import com.yao.geek.common.status.StatusCode;

/**
 * 评论不存在异常
 */
public class CommentNotExist extends FuException {
    public CommentNotExist(String message) {
        super(message);
    }

    public CommentNotExist(StatusCode code) {
        super(code);
    }
}
