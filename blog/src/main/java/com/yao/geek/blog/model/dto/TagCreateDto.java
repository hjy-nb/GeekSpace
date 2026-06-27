package com.yao.geek.blog.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 标签创建DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagCreateDto {
    @NotBlank
    private String name;         //名称
    private String slug;      //标签别名
    private String description;   //标签描述
}
