package com.yao.geek.user.model.query;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.Data;

/**
 * 用户查询参数
 */
@Data
@Builder
public class UserQuery {
    @Min(value = 1)
    @Max(value = 100)
    private Integer page=10;
    @Min(value = 0)
    private Integer size=0;
}
