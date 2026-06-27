package com.yao.geek.auth.model.query;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

/**
 * 用户昵称查询参数
 */
@Data
@Builder
public class NicknameQuery {
    @Min(1)
    @Max(100)
    private Integer page=10;
    @Min(0)
    private Integer size=0;
    @NotBlank
    private String nickname;
}
