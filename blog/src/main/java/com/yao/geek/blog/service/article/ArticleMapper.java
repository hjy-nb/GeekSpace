package com.yao.geek.blog.service.article;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yao.geek.blog.model.entity.ArticleEntity;
import com.yao.geek.blog.model.query.ArticleQuery;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 继承Mapper
 */
public interface ArticleMapper extends BaseMapper<ArticleEntity> {
    //@select也能用mybatis的xml标签
    @Select("""
            
            """)
    List<ArticleEntity> getArticleList(ArticleQuery articleQuery);
}
