package com.yao.geek.auth.model.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserBaseDetailVo {
    private Long id;
    private String username;
    private String nickname;
    private String avatar;
}
