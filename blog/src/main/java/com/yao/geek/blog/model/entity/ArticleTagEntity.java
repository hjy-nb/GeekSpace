package com.yao.geek.blog.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 文章标签关联实体类
 */
@Data
@Builder
@TableName("article_tag_relation")
public class ArticleTagEntity {
    @TableId(value = "id", type = IdType.ASSIGN_ID)  // 雪花算法自动生成
    private Long id;
    @TableField("article_id")
    private Long articleId;
    @TableField("tag_id")
    private Long tagId;
    @TableField(value = "create_time")
    private LocalDateTime createTime;
}
