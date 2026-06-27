package com.yao.geek.user.control;

import com.yao.geek.common.Constant.NumConstant;
import com.yao.geek.user.model.dto.UserDetailDto;
import com.yao.geek.user.model.result.Result;
import com.yao.geek.user.model.vo.UserDetailVo;
import com.yao.geek.user.service.detail.UserDetailService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 用户信息控制器
 */
@RestController
@RequestMapping("/geek/user")
@Validated
public class UserDetailController {
    private final UserDetailService userDetailService;

    public UserDetailController(UserDetailService userDetailService) {
        this.userDetailService = userDetailService;
    }

    // 获取用户信息（获取自己的）
    @GetMapping("/getUser")
    public Result<UserDetailVo> getUserDetail(@RequestHeader(NumConstant.T_ID) Long id){
        return Result.ok(userDetailService.getUserDetail(id));
    }

    // 更新用户信息
    @PutMapping("/updateUser")
    public Result<Void> updateUserDetail(@RequestHeader(NumConstant.T_ID) Long id, @Valid @RequestBody UserDetailDto userDetailDto){
        userDetailService.updateUserDetail(id, userDetailDto);

        return Result.ok(null);
    }

    //删除用户信息（只有注销的时候才能删除）
    @DeleteMapping("/deleteUser")
    public void deleteUserDetail(@RequestParam("id") @NotNull Long id){
        userDetailService.deleteUserDetail(id);
    }

    // 创建默认用户信息,不用认证
    @PostMapping("/createDefaultUser")
    public void createDefaultUserDetail(@RequestParam("id") @NotNull Long id){
        userDetailService.createDefaultUserDetail(id);
    }

    // 获取用户信息（获取别人的）
    @GetMapping("/getUserDetail")
    public Result<UserDetailVo> getOtherUserDetail(@RequestHeader(NumConstant.T_ID) Long id, @RequestParam("userId") @NotNull Long userId){
        return Result.ok(userDetailService.getOtherUserDetail(id,userId));
    }
}
