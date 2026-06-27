package com.yao.geek.blog.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 标签更新DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagUpdateDto {
    private String name;         //名称
    private String slug;      //标签别名
    private String description;   //标签描述
}
