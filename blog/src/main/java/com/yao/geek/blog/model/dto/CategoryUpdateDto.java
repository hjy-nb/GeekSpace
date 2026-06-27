package com.yao.geek.blog.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 分类更新DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryUpdateDto {
    private String name;  // 分类名称
    private String slug;  // 分类别名
    private String description;  // 分类描述
    private Long parentId;          // 父分类ID，默认0
    private Integer sortOrder;      // 排序权重
}
