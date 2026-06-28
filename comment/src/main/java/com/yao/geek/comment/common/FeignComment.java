package com.yao.geek.comment.common;

import jakarta.validation.constraints.NotNull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 调用blog服务
 */
@FeignClient(name="blog",path="/geek/blog")
public interface FeignComment {
    //判断文章存不存在
    @GetMapping("/isArticleExist")
    Boolean isNotArticleExist(@RequestParam("id") @NotNull Long articleId);
}
