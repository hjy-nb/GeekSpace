package com.yao.geek.comment.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 评论点赞DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentLikeDto {
    @NotNull
    private Long commentId; //评论ID
    @NotNull
    private Long userId; //用户ID
}
