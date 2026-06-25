package com.yao.geek.auth.common;

import jakarta.validation.constraints.NotNull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * feign调用user服务
 */
@FeignClient(name = "user", path = "/geek/user")
public interface FeignAuth {
    // 创建默认用户信息
    @PostMapping("/createDefaultUser")
    Boolean createDefaultUserDetail(@RequestParam("id") @NotNull Long id);

    //删除用户信息
    @DeleteMapping("/deleteUser")
    Boolean deleteUserDetail(@RequestParam("id") @NotNull Long id);

    //删除用户所有关注信息
    @DeleteMapping("/deleteAttention")
    Boolean deleteAttention(@RequestParam("id") @NotNull Long attentionId);
}
