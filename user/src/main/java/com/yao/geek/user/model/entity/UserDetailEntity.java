package com.yao.geek.user.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 用户信息表
 */
@TableName("user_profile")
@Data
@Builder
public class UserDetailEntity {            //私人信息应该授权才能看
    @TableId(value="id",type = IdType.INPUT)
    private Long id;   // 用户ID
    @TableField("real_name")
    private String realName;   // 真实姓名
    @TableField("birthday")
    private LocalDate birthday;   // 生日，LocalDate更适合日期
    @TableField("location")
    private String location;  // 所在地
    @TableField("company")
    private String company;  // 公司
    @TableField("position")
    private String position;  // 职位
    @TableField("website")
    private String website;  // 个人网站
    @TableField("github")
    private String github;  // GitHub账户
    @TableField("weibo")
    private String weibo;  // 微博账户
    @TableField("bio")
    private String bio;          // 个人简介
    @TableField("skills")
    private String skills;       // 技能标签
    @TableField(value="create_time")   // 插入时自动填充
    private LocalDateTime createTime;  // 创建时间
    @TableField(value="update_time")
    private LocalDateTime updateTime;  // 更新时间
}
