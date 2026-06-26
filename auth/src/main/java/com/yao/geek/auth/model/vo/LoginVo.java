package com.yao.geek.auth.model.vo;

import lombok.Builder;
import lombok.Data;

/**
 * 登录VO
 */
@Data
@Builder
public class LoginVo {
    private Long userId;
    private String token; // token
}
