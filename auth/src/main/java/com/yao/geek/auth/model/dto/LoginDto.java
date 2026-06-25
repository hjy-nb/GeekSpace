package com.yao.geek.auth.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户名密码登录
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
