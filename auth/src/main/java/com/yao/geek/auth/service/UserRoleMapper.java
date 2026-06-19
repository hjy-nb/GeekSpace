package com.yao.geek.auth.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yao.geek.auth.model.entity.UserRoleEntity;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 继承BaseMapper
 */
public interface UserRoleMapper extends BaseMapper<UserRoleEntity> {
    @Select(value = """
            SELECT auth.a_name
            from auth
            join role_auth on role_auth.a_id=auth.id
            where role_auth.r_id=#{id}
            """)
    public List<String> getAuthority(int id);  // 获取角色权限
}
