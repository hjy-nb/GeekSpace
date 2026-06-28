package com.yao.geek.comment.model.query;

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
    @Min(value = 1)
    private Integer page=1;
    @Min(value = 1)
    @Max(value = 100)
    private Integer size=5;
}
