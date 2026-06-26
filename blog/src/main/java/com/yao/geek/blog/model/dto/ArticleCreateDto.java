package com.yao.geek.blog.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文章创建DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleCreateDto {
    @NotBlank
    private String title;           // 文章标题
    @NotBlank
    private String summary;         // 文章摘要
    private String coverImage;      // 封面图片
    @NotNull
    private Long categoryId;        // 分类ID
    private Boolean isTop=false;    // 是否置顶 0:否 1:是
    @NotNull
    private Boolean isOriginal;     // 是否原创 0:转载 1:原创
    private String originalUrl;     // 原文链接
}
