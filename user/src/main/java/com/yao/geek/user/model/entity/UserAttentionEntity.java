package com.yao.geek.user.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户关注表
 */
@TableName("user_follow")
@Data
@Builder
public class UserAttentionEntity {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @TableField("follower_id")
    private Long attentionId;
    @TableField("following_id")
    private Long beAttentionId;
    @TableField(value = "follow_time")
    private LocalDateTime createTime;
}
