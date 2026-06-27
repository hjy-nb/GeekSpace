package com.yao.geek.blog.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 分类创建DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryCreateDto {
    @NotBlank
    private String name;  // 分类名称
    private String slug;  // 分类别名
    private String description;  // 分类描述
    private Long parentId;          // 父分类ID，默认0

}
