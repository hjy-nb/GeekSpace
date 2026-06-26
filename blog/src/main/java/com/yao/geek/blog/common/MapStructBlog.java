package com.yao.geek.blog.common;

import com.yao.geek.blog.model.dto.ArticleCreateDto;
import com.yao.geek.blog.model.entity.ArticleContentEntity;
import com.yao.geek.blog.model.entity.ArticleEntity;
import com.yao.geek.blog.model.vo.ArticleContentVo;
import com.yao.geek.blog.model.vo.ArticleVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * 自动类型转换
 */
@Mapper(componentModel = "spring")
public interface MapStructBlog {
    @Mapping(target = "authorId", source = "userId")
    ArticleEntity toArticleEntity(ArticleCreateDto articleCreateDto, Long userId);

    ArticleContentVo toArticleContentVo(ArticleContentEntity articleContentEntity);

    ArticleVo toArticleVo(ArticleEntity articleEntity);
}
