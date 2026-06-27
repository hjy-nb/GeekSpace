package com.yao.geek.blog.model.status;

import lombok.Getter;

/**
 * blog实体状态
 */
@Getter
public enum Status {
    //article
    ARTICLE_PUBLISH(1,"文章发布"),
    ARTICLE_DRAFT(0,"文章草稿"),
    ARTICLE_PRIVATE(2,"文章私密"),
    ARTICLE_TOP(3,"文章置顶"),
    ARTICLE_NO_TOP(4,"文章取消置顶"),

    //category
    CATEGORY_HIDDEN(0,"分类隐藏"),
    CATEGORY_DISPLAY(1,"分类显示");


    private final Integer code;
    private final String message;
    Status(Integer code, String message){
        this.code = code;
        this.message = message;
    }
}
