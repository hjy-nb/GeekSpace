package com.yao.geek.user.service.fan;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yao.geek.user.model.entity.UserAttentionEntity;
import com.yao.geek.user.model.vo.UserBaseDetailVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 继承BaseMapper
 */
public interface UserAttentionMapper extends BaseMapper<UserAttentionEntity> {
}
