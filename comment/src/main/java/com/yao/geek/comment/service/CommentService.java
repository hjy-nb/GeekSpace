package com.yao.geek.comment.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.houbb.sensitive.word.bs.SensitiveWordBs;
import com.yao.geek.comment.common.CommentMapStruct;
import com.yao.geek.comment.common.FeignComment;
import com.yao.geek.comment.model.dto.CommentDeleteDto;
import com.yao.geek.comment.model.dto.CommentPublishDto;
import com.yao.geek.comment.model.dto.CommentReplyDto;
import com.yao.geek.comment.model.entity.CommentEntity;
import com.yao.geek.comment.model.query.Query;
import com.yao.geek.comment.model.status.Status;
import com.yao.geek.comment.model.vo.CommentListVo;
import com.yao.geek.comment.model.vo.CommentVo;
import com.yao.geek.comment.service.like.CommentLikeService;
import com.yao.geek.common.exception.ArticleNotExist;
import com.yao.geek.common.exception.CommentNotExist;
import com.yao.geek.common.log.GetLogger;
import com.yao.geek.common.sensitive.SensitiveWord;
import com.yao.geek.common.status.StatusCode;
import org.slf4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * 评论服务类
 */
@Service
public class CommentService extends ServiceImpl<CommentMapper, CommentEntity> implements CommentIService {
    private static final Logger B_LOGGER= GetLogger.getBusinessLogger();

    private final CommentMapStruct commentMapStruct;
    private final FeignComment feignComment;
    private final CommentMapper commentMapper;
    private final CommentLikeService commentLikeService;
    private final SensitiveWordBs sensitiveWordBs=SensitiveWordBs.newInstance();

    public CommentService(CommentMapStruct commentMapStruct, FeignComment feignComment,
                          CommentMapper commentMapper,CommentLikeService commentLikeService) {
        this.commentMapStruct = commentMapStruct;
        this.feignComment = feignComment;
        this.commentMapper = commentMapper;
        this.commentLikeService = commentLikeService;
    }

    //注意，有关操作会使文章的阅读量等发生变化，注意要关联操作.

    //发表评论(有敏感词要审核)
    @Transactional
    public Long publishComment(CommentPublishDto commentPublishDto,Long userId) {
        B_LOGGER.info("发表评论，{}", userId);

        if(feignComment.isNotArticleExist(commentPublishDto.getArticleId())){
            throw new ArticleNotExist(StatusCode.ARTICLE_NOT_EXIST);
        }

        CommentEntity commentEntity = commentMapStruct.toCommentEntity(commentPublishDto, userId);

        if(sensitiveWordBs.contains(commentEntity.getContent())){
            B_LOGGER.info("评论内容包含敏感词待审核，{}", commentEntity.getId());

            commentEntity.setStatus(Status.COMMENT_CHECK.getCode());
        }

        save(commentEntity);

        if(Objects.equals(commentEntity.getStatus(), Status.COMMENT_CHECK.getCode())){
            //进入人工审核
            System.out.println("审核中");
        }

        B_LOGGER.info("发表评论成功，{}", commentEntity.getId());

        return commentEntity.getId();
    }

    //删除评论(管理员)
    @PreAuthorize("hasAuthority('allprivilege')")
    @Transactional
    public void deleteComment(CommentDeleteDto commentDeleteDto) {
        B_LOGGER.info("删除评论，{}", commentDeleteDto.getId());

        if(isNotCommentExist(commentDeleteDto.getId(),commentDeleteDto.getArticleId())){
            throw new CommentNotExist(StatusCode.COMMENT_NOT_EXIST);
        }

        B_LOGGER.info("删除评论点赞关联");

        commentLikeService.deleteCommentLike(commentDeleteDto.getId());

        B_LOGGER.info("删除评论点赞关联成功");

        updateStatus(commentDeleteDto.getId(), Status.COMMENT_DELETE.getCode());


        B_LOGGER.info("删除评论成功");
    }

    //删除评论(用户)
    @Transactional
    public void deleteSelfComment(CommentDeleteDto commentDeleteDto, Long userId) {
        B_LOGGER.info("删除评论 ，{}", commentDeleteDto.getId());

        boolean isCommentExist = lambdaQuery().eq(CommentEntity::getId, commentDeleteDto.getId())
                .eq(CommentEntity::getUserId, userId)
                .eq(CommentEntity::getStatus, Status.COMMENT_PUBLISH.getCode())
                .eq(CommentEntity::getArticleId, commentDeleteDto.getArticleId())
                .exists();

        if(!isCommentExist){
            throw new CommentNotExist(StatusCode.COMMENT_NOT_EXIST);
        }

        B_LOGGER.info("删除评论点赞关联 ");

        commentLikeService.deleteCommentLike(commentDeleteDto.getId());

        B_LOGGER.info("删除评论点赞关联成功 ");

        updateStatus(commentDeleteDto.getId(), Status.COMMENT_DELETE.getCode());


        B_LOGGER.info("删除评论成功 ");
    }

    //回复评论(也要审核)
    @Transactional
    public Long replyComment(CommentReplyDto commentReplyDto, Long userId) {
        B_LOGGER.info("回复评论，{}", userId);

        if(isNotCommentExist(commentReplyDto.getReplyToCommentId()
        , commentReplyDto.getArticleId(),commentReplyDto.getReplyToId())){
            throw new CommentNotExist(StatusCode.COMMENT_NOT_EXIST);
        }

        CommentEntity commentEntity = commentMapStruct.toCommentEntity(commentReplyDto, userId);

        if(sensitiveWordBs.contains(commentEntity.getContent())){
            B_LOGGER.info("评论内容包含敏感词待审核,{}", commentEntity.getId());

            commentEntity.setStatus(Status.COMMENT_CHECK.getCode());
        }

        save(commentEntity);

        if(Objects.equals(commentEntity.getStatus(), Status.COMMENT_CHECK.getCode())){
            //进入人工审核
            System.out.println("审核中");
        }

        B_LOGGER.info("回复评论成功，{}", commentEntity.getId());

        return commentEntity.getId();
    }

    //查看评论
    public CommentVo viewComment(Long commentId){
        B_LOGGER.info("查看评论，{}", commentId);

        CommentEntity commentEntity = getById(commentId);

        if(commentEntity == null){
            throw new CommentNotExist(StatusCode.COMMENT_NOT_EXIST);
        }

        return commentMapStruct.toCommentVo(commentEntity);
    }

    //修改点赞数(内部调用)
    public void updateLikeCount(Long commentId, Integer likeCount) {
        lambdaUpdate().eq(CommentEntity::getId, commentId)
                .set(CommentEntity::getLikeCount, likeCount)
                .update();
    }

    //查看点赞数
    public Integer getLikeCount(Long commentId) {
        B_LOGGER.info("查看点赞数，{}", commentId);

        CommentEntity commentEntity = getById(commentId);

        if(commentEntity == null){
            throw new CommentNotExist(StatusCode.COMMENT_NOT_EXIST);
        }

        return commentEntity.getLikeCount();
    }

    //修改评论状态(内部调用)
    public void updateStatus(Long commentId, Integer status) {
        lambdaUpdate().eq(CommentEntity::getId, commentId)
                .set(CommentEntity::getStatus, status)
                .update();

        if(Objects.equals(status, Status.COMMENT_PUBLISH.getCode())){
            B_LOGGER.info("评论发布，{}", commentId);
        } else if (Objects.equals(status, Status.COMMENT_DELETE.getCode())) {
            B_LOGGER.info("评论删除，{}", commentId);
        }
    }

    //查看评论状态
    public Integer getStatus(Long commentId) {
        B_LOGGER.info("查看评论状态，{}", commentId);

        CommentEntity commentEntity = getById(commentId);

        if(commentEntity == null){
            throw new CommentNotExist(StatusCode.COMMENT_NOT_EXIST);
        }

        return commentEntity.getStatus();
    }

    //获取文章所有评论
    public Page<CommentListVo> getArticleComments(Query query){
        B_LOGGER.info("获取文章{}所有评论", query.getArticleId());

        if(feignComment.isNotArticleExist(query.getArticleId())){
            throw new ArticleNotExist(StatusCode.ARTICLE_NOT_EXIST);
        }

        Page<CommentListVo> page = new Page<>(query.getPage(), query.getSize());

        return commentMapper.getArticleComments(page, query.getArticleId());
    }

    //获取评论所有回复
    public Page<CommentListVo> getCommentReplies(Query query){
        if(query.getReplyToCommentId()==null){
            throw new CommentNotExist(StatusCode.COMMENT_NOT_EXIST);
        }

        B_LOGGER.info("获取评论{}所有回复", query.getReplyToCommentId());

        if(isNotCommentExist(query.getReplyToCommentId(), query.getArticleId())){
            throw new CommentNotExist(StatusCode.COMMENT_NOT_EXIST);
        }

        Page<CommentListVo> page = new Page<>(query.getPage(), query.getSize());

        return commentMapper.getCommentReplies(page, query.getArticleId(), query.getReplyToCommentId());
    }

    //获取热门评论
    public Page<CommentListVo> getArticleHotComments(Query query){
        B_LOGGER.info("获取文章{}热门评论", query.getArticleId());

        if(feignComment.isNotArticleExist(query.getArticleId())){
            throw new ArticleNotExist(StatusCode.ARTICLE_NOT_EXIST);
        }

        Page<CommentListVo> page = new Page<>(query.getPage(), query.getSize());

        return commentMapper.getArticleHotComments(page, query.getArticleId());
    }

    //判断评论存不存在
    public boolean isNotCommentExist(Long commentId) {
        return !lambdaQuery().eq(CommentEntity::getId, commentId)
                .eq(CommentEntity::getStatus, Status.COMMENT_PUBLISH.getCode())
                .exists();
    }

    public boolean isNotCommentExist(Long commentId, Long articleId) {
        return !lambdaQuery().eq(CommentEntity::getId, commentId)
                .eq(CommentEntity::getArticleId, articleId)
                .eq(CommentEntity::getStatus, Status.COMMENT_PUBLISH.getCode())
                .exists();
    }

    public boolean isNotCommentExist(Long commentId, Long articleId, Long userId) {
        return !lambdaQuery().eq(CommentEntity::getId, commentId)
                .eq(CommentEntity::getArticleId, articleId)
                .eq(CommentEntity::getUserId, userId)
                .eq(CommentEntity::getStatus, Status.COMMENT_PUBLISH.getCode())
                .exists();
    }
}
