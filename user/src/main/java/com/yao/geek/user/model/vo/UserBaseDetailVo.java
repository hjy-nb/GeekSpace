package com.yao.geek.user.model.vo;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户基本信息VO
 */
@Data
@Builder
public class UserBaseDetailVo {
    private Long id;
    private String username;
    private String nickname;
    private String email; // 邮箱
    private String phone; // 手机号
    private Integer gender; // 性别 0:未知 1:男 2:女
    private String avatar;
    private LocalDateTime lastLoginTime; // 最后登录时间
    private LocalDateTime createTime; // 创建时间
}

