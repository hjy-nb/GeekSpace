package com.yao.geek.auth.control;

import com.yao.geek.auth.model.dto.LoginDto;
import com.yao.geek.auth.model.dto.RegisterDto;
import com.yao.geek.auth.model.dto.UserOutDto;
import com.yao.geek.auth.model.result.Result;
import com.yao.geek.auth.model.vo.LoginVo;
import com.yao.geek.auth.service.AuthService;
import com.yao.geek.common.Constant.NumConstant;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;

/**
 * 控制层
 */
@RestController
@RequestMapping("/geek/auth")
public class IController {
    private final AuthService authService;

    public IController(AuthService authService) {
        this.authService = authService;
    }

    // 用户登录
    @GetMapping("/login")
    public Result<LoginVo> login(@RequestBody LoginDto loginDto){
        return Result.ok(authService.login(loginDto));
    }

    // 用户注册
    @PostMapping("/register")
    public Result<LoginVo> register(@RequestBody RegisterDto registerDto){
        return Result.ok(authService.register(registerDto));
    }

    // 用户注销
    @PutMapping("/logout")
    public Result<Void> logout(@RequestBody UserOutDto userOutDto, @RequestHeader(NumConstant.T_ID) Long id){
        authService.logout(userOutDto, id);
        return Result.ok(null);
    }

    // 获取验证码,直接写入响应了，不用在返回响应
    @GetMapping("/code")
    public void getCode(HttpServletResponse response) throws IOException {
        authService.getCode(response);
    }
}
