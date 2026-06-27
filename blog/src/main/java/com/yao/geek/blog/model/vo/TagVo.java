package com.yao.geek.blog.model.vo;

import lombok.Builder;
import lombok.Data;

/**
 * 标签vo
 */
@Data
@Builder
public class TagVo {
    private String name;         //名称
    private String slug;      //标签别名
    private String description;   //标签描述
    private Integer articleCount;   //文章数量
}
