package com.yao.geek.comment.model.vo;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 评论Vo
 */
@Data
@Builder
public class CommentVo {
    private Long articleId; //文章ID
    private Long userId; //用户ID
    private Long replyToId; //回复的用户ID
    private Long replyToCommentId; //回复的评论ID
    private String content; //评论内容
    private Integer isAuthor; //是否是作者 0:否 1:是
    private String ipAddress; //IP地址
    private String userAgent; //用户代理
    private LocalDateTime createTime; //创建时间
}
