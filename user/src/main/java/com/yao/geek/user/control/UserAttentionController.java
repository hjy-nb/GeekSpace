package com.yao.geek.user.control;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yao.geek.common.Constant.NumConstant;
import com.yao.geek.user.model.query.UserQuery;
import com.yao.geek.user.model.result.Result;
import com.yao.geek.user.model.vo.UserBaseDetailVo;
import com.yao.geek.user.service.fan.UserAttentionIService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户关注控制类
 */
@RestController
@RequestMapping("/geek/user")
@Validated
public class UserAttentionController {
    private final UserAttentionIService userAttentionIService;
    public UserAttentionController(UserAttentionIService userAttentionIService) {
        this.userAttentionIService = userAttentionIService;
    }
    //关注用户
    @PostMapping("/attention")
    public Result<Void> attentionUser(@RequestHeader(NumConstant.T_ID) Long attentionId, @RequestParam("beAttentionId") @NotNull Long beAttentionId) {
        userAttentionIService.attentionUser(attentionId, beAttentionId);

        return Result.ok(null);
    }

    //取消关注
    @DeleteMapping("/cancelAttention")
    public Result<Void> cancelAttention(@RequestHeader(NumConstant.T_ID) Long attentionId, @RequestParam("beAttentionId") @NotNull Long beAttentionId) {
        userAttentionIService.cancelAttention(attentionId, beAttentionId);

        return Result.ok(null);
    }

    //获取关注列表(要分页)
    @GetMapping("/getAttentionList")
    public Result<Page<UserBaseDetailVo>> getAttentionIdList(@RequestHeader(NumConstant.T_ID) Long attentionId, @Valid @ModelAttribute UserQuery query) {
        return Result.ok(userAttentionIService.getAttentionIdList(attentionId, query));
    }

    //获取粉丝列表（要分页）
    @GetMapping("/getFansList")
    public Result<Page<UserBaseDetailVo>> getFansIdList(@RequestHeader(NumConstant.T_ID) Long beAttentionId,@Valid @ModelAttribute UserQuery query) {
        return Result.ok(userAttentionIService.getFansIdList(beAttentionId, query));
    }

    //删除用户所有关注信息
    @DeleteMapping("/deleteAttention")
    public void deleteAttention(@RequestParam("id") @NotNull Long attentionId) {
        userAttentionIService.deleteUserAttention(attentionId);
    }
}
