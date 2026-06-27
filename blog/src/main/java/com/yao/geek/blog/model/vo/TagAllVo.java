package com.yao.geek.blog.model.vo;

import lombok.Builder;
import lombok.Data;

/**
 * 标签列表vo
 */
@Data
@Builder
public class TagAllVo {
    private Long id;             //id
    private String name;
}
