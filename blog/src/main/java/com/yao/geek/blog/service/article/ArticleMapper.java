package com.yao.geek.blog.service.article;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yao.geek.blog.model.entity.ArticleEntity;
import com.yao.geek.blog.model.query.ArticleQuery;
import com.yao.geek.blog.model.vo.ArticleConditionVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 继承Mapper
 */
public interface ArticleMapper extends BaseMapper<ArticleEntity> {
    //@select也能用mybatis的xml标签,必须用<script> 包裹不能用<select>,里面用到的外部变量，必须用@Param注解映射到sql中

    // 根据条件查询文章列表
    @Select("""
            <script>
              select distinct a.id,a.title,a.summary,a.cover_image
              from article a
              join article_tag_relation atr on a.id = atr.article_id
              <where>
                   a.status=1
                   <if test="articleQuery.title!= null and articleQuery.title!=''">
                       and a.title like CONCAT('%', #{articleQuery.title}, '%')
                   </if>
                   <if test="articleQuery.categoryId != null">
                       and a.category_id = #{articleQuery.categoryId}
                   </if>
                   <if test="articleQuery.tagIds!=null and articleQuery.tagIds.size()>0">
                       and atr.tag_id in
                       <foreach item="tagId" collection="articleQuery.tagIds" open="(" separator="," close=")">
                           #{tagId}
                       </foreach>
                   </if>
              </where>
              order by a.publish_time desc
            </script>
            """)
    Page<ArticleConditionVo> getArticleList(Page<ArticleConditionVo> page, @Param("articleQuery") ArticleQuery articleQuery);  //同名实体类字段也可以直接映射到vo

    //查看热点文章
    @Select("""
            select id,title,summary,cover_image
            from article
            where status=1
            order by like_count desc,comment_count desc,share_count desc,view_count desc
            """)
    Page<ArticleConditionVo> getHotArticleList(Page<ArticleConditionVo> page);

    //查看分类下所有文章
    @Select("""
            select id,title,summary,cover_image
            from article
            where status=1 and category_id=#{categoryId}
            order by publish_time desc
            """)
    Page<ArticleConditionVo> getArticleListByCategory(Page<ArticleConditionVo> page, @Param("categoryId") Long categoryId);
}
