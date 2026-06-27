package com.yao.geek.auth.control;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yao.geek.auth.model.dto.LoginDto;
import com.yao.geek.auth.model.dto.RegisterDto;
import com.yao.geek.auth.model.dto.UserOutDto;
import com.yao.geek.auth.model.query.NicknameQuery;
import com.yao.geek.auth.model.query.UserQuery;
import com.yao.geek.auth.model.result.Result;
import com.yao.geek.auth.model.vo.LoginVo;
import com.yao.geek.auth.model.vo.UserBaseDetailVo;
import com.yao.geek.auth.service.AuthService;
import com.yao.geek.common.Constant.NumConstant;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.List;

/**
 * 控制层
 */
@RestController
@RequestMapping("/geek/auth")
@Validated
public class IController {
    private final AuthService authService;

    public IController(AuthService authService) {
        this.authService = authService;
    }

    // 用户登录
    @GetMapping("/login")
    public Result<LoginVo> login(@Valid @RequestBody LoginDto loginDto){
        return Result.ok(authService.login(loginDto));
    }

    // 用户注册
    @PostMapping("/register")
    public Result<LoginVo> register(@Valid @RequestBody RegisterDto registerDto){
        return Result.ok(authService.register(registerDto));
    }

    // 用户注销
    @DeleteMapping("/logout")
    public Result<Void> logout(@Valid @RequestBody UserOutDto userOutDto, @RequestHeader(NumConstant.T_ID) Long id){
        authService.logout(userOutDto, id);

        return Result.ok(null);
    }

    // 获取验证码,直接写入响应了，不用在返回响应
    @GetMapping("/code")
    public void getCode(HttpServletResponse response) throws IOException {
        authService.getCode(response);
    }

    // 昵称模糊查询
    @GetMapping("/nickname")
    public Result<Page<UserBaseDetailVo>> getUserIdByNickname(@ModelAttribute @Valid NicknameQuery query){
        return Result.ok(authService.getUserIdByNickname(query));
    }

    // 用户基本信息查询
    @GetMapping("/userbase")
    public Page<UserBaseDetailVo> getUserBaseDetail(@RequestParam(value = "ids") @NotNull @NotEmpty List<Long> ids, @ModelAttribute @Valid UserQuery query){
        return authService.getUserBaseDetail(ids, query);
    }
}
