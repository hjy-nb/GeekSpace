package com.yao.geek.blog.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 文章实体类
 */
@Data
@Builder
@TableName("article")
public class ArticleEntity {
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;                // 文章ID
    @TableField("title")
    private String title;           // 文章标题
    @TableField("summary")
    private String summary;         // 文章摘要
    @TableField("cover_image")
    private String coverImage;      // 封面图片
    @TableField("author_id")
    private Long authorId;          // 作者ID
    @TableField("category_id")
    private Long categoryId;        // 分类ID
    @TableField("status")
    private Integer status;         // 状态 0:草稿 1:已发布 2:私密   //不用逻辑删除自己搜索时判断
    @TableField("view_count")
    private Integer viewCount;      // 阅读数
    @TableField("like_count")
    private Integer likeCount;      // 点赞数
    @TableField("comment_count")
    private Integer commentCount;   // 评论数
    @TableField("share_count")
    private Integer shareCount;     // 分享数
    @TableField("is_top")
    private Boolean isTop;          // 是否置顶 0:否 1:是
    @TableField("is_original")
    private Boolean isOriginal;     // 是否原创 0:转载 1:原创
    @TableField("original_url")
    private String originalUrl;     // 原文链接
    @TableField("publish_time")
    private LocalDateTime publishTime;  // 发布时间
    @TableField("create_time")
    private LocalDateTime createTime;   // 创建时间
    @TableField("update_time")
    private LocalDateTime updateTime;   // 更新时间
}
