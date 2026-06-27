package com.yao.geek.blog.service.tag;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yao.geek.blog.model.entity.TagEntity;
import com.yao.geek.blog.model.vo.TagAllVo;
import org.apache.ibatis.annotations.Select;

/**
 * 继承baseMapper
 */
public interface TagMapper extends BaseMapper<TagEntity> {
    //查询所有标签
    @Select("""
            select id,name
            from tag
            """)
    Page<TagAllVo> getAllTags(Page<TagAllVo> page);

    //查询热门标签
    @Select("""
            select id,name
            from tag
            order by article_count desc
            """)
    Page<TagAllVo> getHotTags(Page<TagAllVo> page);
}
