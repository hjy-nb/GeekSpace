package com.yao.geek.auth.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

/**
 * 用户角色表
 */
@TableName("role_user")
@Data
@Builder
public class UserRoleEntity {
    @TableField(value = "user_id")
    private Long userId;
    @TableField(value = "role_id")
    private int roleId;
}
