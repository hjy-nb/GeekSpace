package com.yao.geek.comment.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 评论点赞实体类
 */
@Data
@Builder
@TableName("comment_like")
public class CommentLikeEntity {
    @TableId(value="id", type= IdType.ASSIGN_ID)
    private Long id; //主键
    @TableField("comment_id")
    private Long commentId; //评论ID
    @TableField("user_id")
    private Long userId; //用户ID
    @TableField("like_time")
    private LocalDateTime likeTime; //点赞时间
}
