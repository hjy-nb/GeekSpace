package com.yao.geek.auth.model.query;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户查询Query
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserQuery {
    @Min(value = 1)
    private Integer page=1;
    @Min(value = 1)
    @Max(value = 100)
    private Integer size=10;
}
