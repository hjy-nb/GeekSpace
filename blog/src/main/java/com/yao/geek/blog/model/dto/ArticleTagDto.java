package com.yao.geek.blog.model.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 文章标签映射Dto
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleTagDto {
    @NotNull
    private Long articleId;
    @NotNull
    @NotEmpty
    private List<Long> tagIds;
}
