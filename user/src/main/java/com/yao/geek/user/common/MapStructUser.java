package com.yao.geek.user.common;

import com.yao.geek.user.model.entity.UserDetailEntity;
import com.yao.geek.user.model.vo.UserDetailVo;
import org.mapstruct.Mapper;

/**
 * MapStruct映射，自动类型转换
 */
@Mapper(componentModel = "spring")
public interface MapStructUser {
    UserDetailVo toUserDetailVo(UserDetailEntity userDetailEntity);
}
