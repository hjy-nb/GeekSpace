package com.yao.geek.comment.model.status;

import lombok.Getter;

/**
 * Comment状态
 */
@Getter
public enum Status {
    COMMENT_CHECK(0, "评论审核中"),
    COMMENT_PUBLISH(1, "评论发布"),
    COMMENT_DELETE(2, "评论删除");

    private final Integer code;
    private final String message;

    Status(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
