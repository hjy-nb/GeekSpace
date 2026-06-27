package com.yao.geek.blog.common;

import com.yao.geek.blog.model.dto.ArticleCreateDto;
import com.yao.geek.blog.model.dto.ArticleTagDto;
import com.yao.geek.blog.model.dto.CategoryCreateDto;
import com.yao.geek.blog.model.dto.TagCreateDto;
import com.yao.geek.blog.model.entity.*;
import com.yao.geek.blog.model.vo.*;
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

    ArticleConditionVo toArticleConditionVo(ArticleEntity articleEntity);

    TagEntity toTagEntity(TagCreateDto tagCreateDto);

    TagVo toTagVo(TagEntity tagEntity);

    @Mapping(target = "articleId", source = "articleId")
    @Mapping(target = "tagId", source = "tagId")
    ArticleTagEntity toArticleTagEntity(Long articleId, Long tagId);

    @Mapping(target = "level", source = "level")
    CategoryEntity toCategoryEntity(CategoryCreateDto categoryCreateDto,Integer level);

    CategoryVo toCategoryVo(CategoryEntity categoryEntity);
}
