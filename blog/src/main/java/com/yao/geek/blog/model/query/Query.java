package com.yao.geek.blog.model.query;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 普通查询Query
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Query {
    @Min(1)
    private Integer page=1;  // 初始页码页码
    @Min(1)
    @Max(100)
    private Integer size=10;  // 每页大小
}
