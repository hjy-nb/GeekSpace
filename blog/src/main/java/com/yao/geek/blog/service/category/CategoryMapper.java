package com.yao.geek.blog.service.category;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yao.geek.blog.model.entity.CategoryEntity;
import com.yao.geek.blog.model.vo.CategoryAllVo;
import org.apache.ibatis.annotations.Select;

/**
 * 继承BaseMapper
 */
public interface CategoryMapper extends BaseMapper<CategoryEntity> {
    //显示所有分类
    @Select("""
           select id,name,description
           from category
           while is_visible=1
           """)
    Page<CategoryAllVo> showAllCategories(Page<CategoryAllVo> page);

    //获取热门分类
    @Select("""
           select id,name,description
           from category
           where is_visible=1
           order by article_count desc
           """)
    Page<CategoryAllVo> getHotCategories(Page<CategoryAllVo> page);
}
