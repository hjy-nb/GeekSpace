package com.yao.geek.comment.common;

import jakarta.validation.constraints.NotNull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 调用auth
 */
@FeignClient(name="auth",path="/geek/auth")
public interface FeignCommentTwo {
    //判断用户是否存在
    @GetMapping("/exist")
    Boolean UserIsNotExist(@RequestParam("id") @NotNull Long id);
}
