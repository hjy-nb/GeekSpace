package com.yao.geek.comment.service.like;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yao.geek.comment.common.CommentMapStruct;
import com.yao.geek.comment.common.FeignCommentTwo;
import com.yao.geek.comment.model.dto.CommentLikeDto;
import com.yao.geek.comment.model.entity.CommentLikeEntity;
import com.yao.geek.comment.model.query.Query;
import com.yao.geek.comment.service.CommentService;
import com.yao.geek.common.exception.CommentNotExist;
import com.yao.geek.common.exception.UserNotExist;
import com.yao.geek.common.log.GetLogger;
import com.yao.geek.common.status.StatusCode;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 评论点赞服务类
 */
@Service
public class CommentLikeService extends ServiceImpl<CommentLikeMapper, CommentLikeEntity> implements CommentLikeIService {
    private static final Logger B_LOGGER= GetLogger.getBusinessLogger();
    private final CommentService commentService;
    private final FeignCommentTwo feignCommentTwo;
    private final CommentMapStruct commentMapStruct;
    private final CommentLikeMapper commentLikeMapper;

    public CommentLikeService(CommentService commentService, FeignCommentTwo feignCommentTwo,
                              CommentMapStruct commentMapStruct ,CommentLikeMapper commentLikeMapper) {
        this.commentService = commentService;
        this.feignCommentTwo = feignCommentTwo;
        this.commentMapStruct = commentMapStruct;
        this.commentLikeMapper =commentLikeMapper;
    }

    //点赞(要关联修改点赞数操作)
    @Transactional
    public Long likeComment(CommentLikeDto commentLikeDto) {
        B_LOGGER.info("点赞评论，{}", commentLikeDto.toString());

        if(commentService.isNotCommentExist(commentLikeDto.getCommentId())){
            throw new CommentNotExist(StatusCode.COMMENT_NOT_EXIST);
        }

        if(feignCommentTwo.UserIsNotExist(commentLikeDto.getUserId())){
            throw new UserNotExist(StatusCode.USER_NOT_EXIST);
        }

        CommentLikeEntity commentLikeEntity = commentMapStruct.toCommentLikeEntity(commentLikeDto);

        save(commentLikeEntity);

        B_LOGGER.info("点赞评论成功");

        return commentLikeEntity.getId();
    }

    //删除评论点赞
    @Transactional
    public void deleteCommentLike(Long id) {
        remove(Wrappers.lambdaQuery(CommentLikeEntity.class)
                .eq(CommentLikeEntity::getCommentId, id));
    }

    //查看点赞列表
    public Page<Long> getLikeList(Query query) {
        if(query.getReplyToCommentId() == null){
            throw new CommentNotExist(StatusCode.COMMENT_NOT_EXIST);
        }
        B_LOGGER.info("查看点赞列表，{}", query.getReplyToCommentId());

        if(commentService.isNotCommentExist(query.getReplyToCommentId(), query.getArticleId())){
            throw new CommentNotExist(StatusCode.COMMENT_NOT_EXIST);
        }

        Page<Long> page = new Page<>();

        return commentLikeMapper.getLikeList(page, query.getReplyToCommentId());
    }
}
