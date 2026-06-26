package com.yao.geek.blog.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 标签实体类
 */
@Data
@Builder
@TableName("tag")
public class TagEntity {
    @TableId(value = "id", type = IdType.ASSIGN_ID) // 雪花算法自动生成
    private Long id;             //id
    @TableField("name")
    private String name;         //名称
    @TableField("slug")
    private String slug;      //标签别名
    @TableField("description")
    private String description;   //标签描述
    @TableField("article_count")
    private Integer articleCount;   //文章数量
    @TableField(value = "create_time")
    private LocalDateTime createTime; //创建时间
    @TableField(value = "update_time")
    private LocalDateTime updateTime; //更新时间
}
