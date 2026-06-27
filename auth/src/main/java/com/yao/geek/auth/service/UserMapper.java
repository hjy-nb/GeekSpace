package com.yao.geek.auth.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yao.geek.auth.model.entity.UserEntity;
import com.yao.geek.auth.model.vo.UserBaseDetailVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 继承BaseMapper
 */
public interface UserMapper extends BaseMapper<UserEntity> {
    // 获取权限名
    @Select(value = """
            SELECT auth.a_name
            from auth
            join role_auth on role_auth.a_id=auth.id
            join role_user on role_user.role_id=role_auth.r_id
            where role_user.user_id=#{id}
            """)
    List<String> getAuthority(@Param("id") Long id);

    //批量获取用户信息
    @Select("""
           <script>
             select user.id,user.username,user.nickname,user.email,user.phone,
                    user.avatar,user.gender,user.last_login_time,user.create_time
             form user
             where user.id in
             <foreach item="id" collection="ids" separator="," open="(" close=")">
                 #{id}
             </foreach>
           </script>
           """)
    Page<UserBaseDetailVo> getUserBaseDetail(Page<UserBaseDetailVo> page,@Param("ids") List<Long> ids);

    //根据用户昵称模糊查询
    @Select("""
           select user.id,user.username,user.nickname,user.email,user.phone,
                    user.avatar,user.gender,user.last_login_time,user.create_time
           form user
           where user.nickname like concat('%',#{nickname},'%')
           """)
    Page<UserBaseDetailVo> getUserIdByNickname(Page<UserBaseDetailVo> page, @Param("nickname") String nickname);

}
