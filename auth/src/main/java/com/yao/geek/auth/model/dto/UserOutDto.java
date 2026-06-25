package com.yao.geek.auth.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户注销
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserOutDto {
    @NotBlank
    private String password;
}
