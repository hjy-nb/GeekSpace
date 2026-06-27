package com.yao.geek.blog.model.vo;

import lombok.Builder;
import lombok.Data;

/**
 * 文章内容vo
 */
@Data
@Builder
public class ArticleContentVo {
    private String markdownContent;   // Markdown内容
    private String content;           // 文章内容
    private Integer wordCount;        // 字数统计
}
