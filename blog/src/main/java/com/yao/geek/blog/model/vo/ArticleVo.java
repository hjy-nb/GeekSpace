package com.yao.geek.blog.model.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 文章vo
 */
@Data
@Builder
public class ArticleVo {
    private String title;           // 文章标题
    private String summary;         // 文章摘要
    private String coverImage;      // 封面图片
    private Long categoryId;        // 分类ID
    private Boolean isOriginal;     // 是否原创 0:转载 1:原创
    private String originalUrl;     // 原文链接
    private LocalDateTime publishTime;  // 发布时间
    private LocalDateTime createTime;   // 创建时间
    private LocalDateTime updateTime;   // 更新时间
}
