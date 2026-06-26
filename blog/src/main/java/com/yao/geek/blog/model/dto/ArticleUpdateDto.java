package com.yao.geek.blog.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文章元数据更新Dto
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleUpdateDto {
    private String title;           // 文章标题
    private String summary;         // 文章摘要
    private String coverImage;      // 封面图片
    private Long categoryId;        // 分类ID
}
