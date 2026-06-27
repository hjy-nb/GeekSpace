package com.yao.geek.blog.service.tag.articletag;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yao.geek.blog.model.entity.ArticleTagEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 继承baseMapper
 */
public interface ArticleTagMapper extends BaseMapper<ArticleTagEntity> {
    //查询文章所有标签
    @Select("""
            select tag_id
            from article_tag_relation
            where article_id = #{articleId}
            """)
    Page<Long> getTagAllVoByArticleId(Page<Long> page, @Param("articleId") Long articleId);

    //查询标签所有文章
    @Select("""
            select article_id
            from article_tag_relation
            where tag_id = #{tagId}
            """)
    Page<Long> getArticleAllVoByTagId(Page<Long> page, @Param("tagId") Long tagId);

}
