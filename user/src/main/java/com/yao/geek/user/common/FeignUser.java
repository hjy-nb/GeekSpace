package com.yao.geek.user.common;

import com.yao.geek.user.model.vo.UserBaseDetailVo;
import jakarta.validation.constraints.NotNull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * feign调用auth服务
 */
@FeignClient(name = "auth",path = "/path/auth")
public interface FeignUser {
    //注意发出的请求也可能被security拦截

    //查询用户基本信息
    @GetMapping("/userbase")
    List<UserBaseDetailVo> getUserBaseDetail(@RequestParam(value = "ids") @NotNull List<Long> ids);
}
