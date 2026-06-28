package com.yao.geek.comment.model.query;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 查询Query
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Query {
    @NotNull
    private Long articleId; //文章ID
    private Long replyToCommentId; //回复的评论ID
    @Min(value = 1)
    private Integer page=1;
    @Min(value = 1)
    @Max(value = 100)
    private Integer size=10;
}
