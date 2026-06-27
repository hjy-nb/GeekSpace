package com.yao.geek.blog.model.vo;

import lombok.Builder;
import lombok.Data;

/**
 * 分类vo
 */
@Data
@Builder
public class CategoryVo {
    private String name;  // 分类名称
    private String slug;  // 分类别名
    private String description;  // 分类描述
    private Long parentId;          // 父分类ID，默认0
    private Integer level;          // 分类层级
    private Integer sortOrder;      // 排序权重
}
