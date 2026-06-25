package com.yao.geek.user.control;

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
    public Result<Boolean> attentionUser(@RequestHeader(NumConstant.T_ID) Long attentionId, @RequestBody @NotNull Long beAttentionId) {
        return Result.ok(userAttentionIService.attentionUser(attentionId, beAttentionId) );
    }

    //取消关注
    @DeleteMapping("/cancelAttention")
    public Result<Boolean> cancelAttention(@RequestHeader(NumConstant.T_ID) Long attentionId, @RequestBody @NotNull Long beAttentionId) {
        return Result.ok(userAttentionIService.cancelAttention(attentionId, beAttentionId));
    }

    //获取关注列表(要分页)
    @GetMapping("/getAttentionList")
    public Result<List<UserBaseDetailVo>> getAttentionIdList(@RequestHeader(NumConstant.T_ID) Long attentionId,@Valid @ModelAttribute UserQuery query) {
        return Result.ok(userAttentionIService.getAttentionIdList(attentionId));
    }

    //获取粉丝列表（要分页）
    @GetMapping("/getFansList")
    public Result<List<UserBaseDetailVo>> getFansIdList(@RequestHeader(NumConstant.T_ID) Long beAttentionId,@Valid @ModelAttribute UserQuery query) {
        return Result.ok(userAttentionIService.getFansIdList(beAttentionId));
    }

    //删除用户所有关注信息
    @DeleteMapping("/deleteAttention")
    public Boolean deleteAttention(@RequestParam("id") @NotNull Long attentionId) {
        return userAttentionIService.deleteUserAttention(attentionId);
    }
}
