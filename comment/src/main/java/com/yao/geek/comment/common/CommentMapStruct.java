package com.yao.geek.comment.common;

import com.yao.geek.comment.model.dto.CommentLikeDto;
import com.yao.geek.comment.model.dto.CommentPublishDto;
import com.yao.geek.comment.model.dto.CommentReplyDto;
import com.yao.geek.comment.model.entity.CommentEntity;
import com.yao.geek.comment.model.entity.CommentLikeEntity;
import com.yao.geek.comment.model.vo.CommentVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * 自动类型转换
 */
@Mapper(componentModel = "spring")
public interface CommentMapStruct {
    @Mapping(target = "userId", source = "userId")
    CommentEntity toCommentEntity(CommentPublishDto commentPublishDto,Long userId);

    @Mapping(target = "userId", source = "userId")
    CommentEntity toCommentEntity(CommentReplyDto commentReplyDto, Long userId);

    CommentVo toCommentVo(CommentEntity commentEntity);

    CommentLikeEntity toCommentLikeEntity(CommentLikeDto commentLikeDto);
}
