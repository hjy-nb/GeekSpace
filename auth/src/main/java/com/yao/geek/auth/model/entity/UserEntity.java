package com.yao.geek.auth.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户表
 */
@TableName("user")
@Data
@Builder
public class UserEntity {
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id; // 用户ID
    @TableField(value = "username")
    private String username; // 用户名
    @TableField(value = "nickname")
    private String nickname; // 昵称
    @TableField(value = "email")
    private String email; // 邮箱
    @TableField(value = "phone")
    private String phone; // 手机号
    @TableField(value = "avatar")
    private String avatar; // 头像URL
    @TableField(value = "gender")
    private Integer gender; // 性别 0:未知 1:男 2:女
    @TableField(value = "password_hash")
    private String passwordHash; // 密码哈希
    @TableField(value = "status")
    private Integer status; // 状态 0:禁用 1:正常 2:锁定
    @TableField(value = "last_login_time")
    private LocalDateTime lastLoginTime; // 最后登录时间
    @TableField(value = "create_time")
    private LocalDateTime createTime; // 创建时间
    @TableField(value = "update_time")
    private LocalDateTime updateTime; // 更新时间
}
