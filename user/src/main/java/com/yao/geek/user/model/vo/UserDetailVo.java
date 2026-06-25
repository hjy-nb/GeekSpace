package com.yao.geek.user.model.vo;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 用户详情VO
 */
@Data
@Builder
public class UserDetailVo {
    private String realName;   // 真实姓名
    private LocalDate birthday;   // 生日，LocalDate更适合日期
    private String location;  // 所在地
    private String company;  // 公司
    private String position;  // 职位
    private String website;  // 个人网站
    private String github;  // GitHub账户
    private String weibo;  // 微博账户
    private String bio;          // 个人简介
    private String skills;       // 技能标签
    private LocalDateTime createTime;  // 创建时间
    private LocalDateTime updateTime;  // 更新时间
}
