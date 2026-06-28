package com.yao.geek.comment.model.vo;

import lombok.Builder;
import lombok.Data;

/**
 * 评论列表VO
 */
@Data
@Builder
public class CommentListVo {
    private Long id; //评论ID
    private Long userId; //用户ID
}
