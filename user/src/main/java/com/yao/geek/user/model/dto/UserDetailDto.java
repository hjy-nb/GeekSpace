package com.yao.geek.user.model.dto;

import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * 用户信息DTO
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailDto {
    private String realName;   // 真实姓名
    @Past(message = "生日不能大于当前时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;   // 生日，LocalDate更适合日期
    private String location;  // 所在地
    private String company;  // 公司
    private String position;  // 职位
    @URL
    private String website;  // 个人网站
    private String github;  // GitHub账户
    private String weibo;  // 微博账户
    private String bio;          // 个人简介
    private String skills;       // 技能标签
}
