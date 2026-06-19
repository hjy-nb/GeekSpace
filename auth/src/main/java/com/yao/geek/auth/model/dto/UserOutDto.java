package com.yao.geek.auth.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

/**
 * 用户注销
 */
@Data
@Builder
public class UserOutDto {
    @NotBlank
    private String password;
}
