package com.yao.geek.auth.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

/**
 * 注册
 */
@Data
@Builder
public class RegisterDto {
    @NotBlank
    private String username;  // 用户名*
    @NotBlank
    private String password;  // 密码*
    @NotBlank
    private String code;      // 验证码*
    @Pattern(regexp = "^1[3-9]\\d{9}$")
    private String phone;  // 手机号
    @Email
    private String email;  // 邮箱
    private String nickname;  // 昵称
}
