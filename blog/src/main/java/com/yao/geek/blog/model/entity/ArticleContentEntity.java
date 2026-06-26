package com.yao.geek.blog.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

/**
 * 文章内容实体类
 */
@Data
@Builder
@TableName("article_content")
public class ArticleContentEntity {
    @TableId(value = "id", type = IdType.INPUT)
    private Long id;                  // 主键（与article.id相同）
    @TableField("markdown_content")
    private String markdownContent;   // Markdown内容
    @TableField("content")
    private String content;           // 文章内容
    @TableField("word_count")
    private Integer wordCount;        // 字数统计
    @TableField("content_hash")
    private String contentHash;       // 内容哈希（用于去重）避免提交相同内容的文章
}
