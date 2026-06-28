package com.yao.geek.comment.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yao.geek.comment.model.entity.CommentEntity;
import com.yao.geek.comment.model.vo.CommentListVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 继承BaseMapper
 */
public interface CommentMapper extends BaseMapper<CommentEntity> {
    //获取文章所有评论
    @Select("""
           select id, user_id
           form comment
           where article_id = #{articleId} and status=1
           and reply_to_id is null and reply_to_comment_id is null
           """)
    Page<CommentListVo> getArticleComments(Page<CommentListVo> page, @Param("articleId") Long articleId);

    //获取评论所有回复
    @Select("""
           select id, user_id
           form comment
           where article_id = #{articleId} and status=1
           and reply_to_comment_id = #{replyToCommentId}
           """)
    Page<CommentListVo> getCommentReplies(Page<CommentListVo> page,@Param("articleId") Long articleId,@Param("replyToCommentId") Long replyToCommentId);

    //获取文章热门评论
    @Select("""
           select id, user_id
           form comment
           where article_id = #{articleId} and status=1
           and reply_to_id is null and reply_to_comment_id is null
           order by like_count desc
           """)
    Page<CommentListVo> getArticleHotComments(Page<CommentListVo> page, @Param("articleId") Long articleId);
}
