package com.yao.geek.user.model.vo;

import lombok.Builder;
import lombok.Data;

/**
 * 用户基本信息VO
 */
@Data
@Builder
public class UserBaseDetailVo {
    private Long id;
    private String username;
    private String nickname;
    private String avatar;
}

