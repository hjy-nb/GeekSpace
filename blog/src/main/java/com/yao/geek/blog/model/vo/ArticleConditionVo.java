package com.yao.geek.blog.model.vo;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 文章条件查询vo
 */
@Data
@Builder
public class ArticleConditionVo {
    private Long id;               // 文章ID
    private String title;           // 文章标题
    private String summary;         // 文章摘要
    private String coverImage;      // 封面图片
}
