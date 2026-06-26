package com.yao.geek.blog.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 分类实体类
 */
@Data
@Builder
@TableName("category")
public class CategoryEntity {
    @TableId(value = "id", type = IdType.ASSIGN_ID)  // 雪花算法自动生成
    private Long id;  // 分类ID
    @TableField("name")
    private String name;  // 分类名称
    @TableField("slug")
    private String slug;  // 分类别名
    @TableField("description")
    private String description;  // 分类描述
    @TableField("parent_id")
    private Long parentId;          // 父分类ID，默认0
    @TableField("level")
    private Integer level;          // 分类层级，tinyint → Integer
    @TableField("sort_order")
    private Integer sortOrder;      // 排序权重
    @TableField("article_count")
    private Integer articleCount;   // 文章数量
    @TableField("is_visible")
    private Integer isVisible;      // 是否可见 0隐藏 1显示
    @TableField(value = "create_time")
    private LocalDateTime createTime;
    @TableField(value = "update_time")
    private LocalDateTime updateTime;
}
