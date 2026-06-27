package com.yao.geek.blog.model.vo;

import lombok.Builder;
import lombok.Data;

/**
 * 所有分类Vo
 */
@Data
@Builder
public class CategoryAllVo {
    private Long id;
    private String name;
    private String description;
}
