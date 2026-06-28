package com.yao.geek.comment.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 评论回复DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentReplyDto {
    @NotNull
    private Long articleId; //文章ID
    @NotNull
    private Long replyToId; //回复的用户ID
    @NotNull
    private Long replyToCommentId; //回复的评论ID
    @NotBlank
    private String content; //评论内容
    @NotNull
    private Integer isAuthor; //是否是作者 0:否 1:是
    private String ipAddress; //IP地址
    private String userAgent; //用户代理
}
