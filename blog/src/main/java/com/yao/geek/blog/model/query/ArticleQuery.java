package com.yao.geek.blog.model.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 组合查询Query
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleQuery {
    private String title;    // 标题
    private Long categoryId;        // 分类ID
    private List<Long> tagIds;        // 标签ID列表
    private Integer page=0;  // 初始页码页码
    private Integer size=10;  // 每页大小
}
