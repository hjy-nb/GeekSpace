package com.yao.geek.auth.common;

import com.yao.geek.auth.model.entity.UserEntity;
import com.yao.geek.auth.model.vo.UserBaseDetailVo;
import org.mapstruct.Mapper;

/**
 * MapStruct映射，自动类型转换
 */
@Mapper(componentModel = "spring")
public interface MapStructAuth {
    UserBaseDetailVo toUserBaseDetailVo(UserEntity userEntity);
}
