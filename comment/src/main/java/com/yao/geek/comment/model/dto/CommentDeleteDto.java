package com.yao.geek.comment.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 删除评论Dto
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDeleteDto {
    @NotNull
    private Long id; //评论ID
    @NotNull
    private Long articleId; //文章ID
}
