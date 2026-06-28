package com.yao.geek.comment.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 评论实体类
 */
@Data
@Builder
@TableName("comment")
public class CommentEntity {
    @TableId(value="id", type= IdType.ASSIGN_ID)
    private Long id; //评论ID
    @TableField("article_id")
    private Long articleId; //文章ID
    @TableField("user_id")
    private Long userId; //用户ID
    @TableField("reply_to_id")
    private Long replyToId; //回复的用户ID
    @TableField("reply_to_comment_id")
    private Long replyToCommentId; //回复的评论ID
    @TableField("content")
    private String content; //评论内容
    @TableField("like_count")
    private Integer likeCount; //点赞数
    @TableField("status")
    private Integer status; //状态 0:待审核 1:已发布 2:已删除 3:垃圾评论
    @TableField("is_author")
    private Integer isAuthor; //是否是作者 0:否 1:是
    @TableField("ip_address")
    private String ipAddress; //IP地址
    @TableField("user_agent")
    private String userAgent; //用户代理
    @TableField("create_time")
    private LocalDateTime createTime; //创建时间
    @TableField("update_time")
    private LocalDateTime updateTime; //更新时间
}
