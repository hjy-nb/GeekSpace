package com.yao.geek.auth.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

/**
 * 用户名密码登录
 */
@Data
@Builder
public class LoginDto {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
