package com.yao.geek.user.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 关注DTO
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAttentionDto {
    @NotNull
    private Long userId;
}
