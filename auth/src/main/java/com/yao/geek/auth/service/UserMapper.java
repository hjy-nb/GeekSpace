package com.yao.geek.auth.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yao.geek.auth.model.entity.UserEntity;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 继承BaseMapper
 */
public interface UserMapper extends BaseMapper<UserEntity> {

    @Select(value = """
            SELECT auth.a_name
            from auth
            join role_auth on role_auth.a_id=auth.id
            join role_user on role_user.role_id=role_auth.r_id
            where role_user.user_id=#{id}
            """)
    public List<String> getAuthority(Long id);  // 获取权限

}
