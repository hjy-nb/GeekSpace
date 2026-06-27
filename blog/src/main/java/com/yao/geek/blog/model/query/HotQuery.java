package com.yao.geek.blog.model.query;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 热门查询Query
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HotQuery {
    @Min(1)
    private Integer page=1;  // 初始页码页码
    @Min(1)
    @Max(20)
    private Integer size=5;  // 每页大小
}
