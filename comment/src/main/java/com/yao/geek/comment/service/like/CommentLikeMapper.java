package com.yao.geek.comment.service.like;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yao.geek.comment.model.entity.CommentLikeEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 继承BaseMapper
 */
public interface CommentLikeMapper extends BaseMapper<CommentLikeEntity> {
    //查看点赞列表
    @Select("""
           select user_id
           from comment_like
           where comment_id = #{commentId}
           """)
    Page<Long> getLikeList(Page<Long> page, @Param("commentId") Long commentId);
}
