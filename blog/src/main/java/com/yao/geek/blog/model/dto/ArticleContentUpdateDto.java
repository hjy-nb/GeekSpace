package com.yao.geek.blog.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文章内容更新DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleContentUpdateDto {
    @NotNull
    private Long id;            // 主键（与article.id相同）
    private String markdownContent;   // Markdown内容
    @NotBlank
    private String content;           // 文章内容
}
