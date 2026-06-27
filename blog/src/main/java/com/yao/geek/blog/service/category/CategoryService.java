package com.yao.geek.blog.service.category;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yao.geek.blog.common.MapStructBlog;
import com.yao.geek.blog.model.dto.CategoryCreateDto;
import com.yao.geek.blog.model.dto.CategoryUpdateDto;
import com.yao.geek.blog.model.entity.CategoryEntity;
import com.yao.geek.blog.model.query.HotQuery;
import com.yao.geek.blog.model.query.Query;
import com.yao.geek.blog.model.status.Status;
import com.yao.geek.blog.model.vo.CategoryAllVo;
import com.yao.geek.blog.model.vo.CategoryVo;
import com.yao.geek.common.exception.CateGoryNotExist;
import com.yao.geek.common.exception.CategoryExist;
import com.yao.geek.common.log.GetLogger;
import com.yao.geek.common.status.StatusCode;
import org.slf4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Objects;

/**
 * 分类服务类
 */
@Service
public class CategoryService extends ServiceImpl<CategoryMapper, CategoryEntity> implements CategoryIService {
    private static final Logger B_LOGGER= GetLogger.getBusinessLogger();

    private final MapStructBlog mapStructBlog;
    private final CategoryMapper categoryMapper;

    public CategoryService(MapStructBlog mapStructBlog, CategoryMapper categoryMapper) {
        this.mapStructBlog = mapStructBlog;
        this.categoryMapper = categoryMapper;
    }

    //创建分类
    @PreAuthorize("hasAuthority('allprivilege')")
    @Transactional
    public Long createCategory(CategoryCreateDto categoryCreateDto){
        B_LOGGER.info("创建分类,{}",categoryCreateDto.getName());

        if(!isNotCategoryExist(categoryCreateDto.getName())){
            throw new CategoryExist(StatusCode.CATEGORY_EXIST);
        }

        if(categoryCreateDto.getParentId()!=null && isNotCategoryExist(categoryCreateDto.getParentId())){
            throw new CateGoryNotExist(StatusCode.CATEGORY_NOT_EXIST);
        }

        int level=1;

        if(categoryCreateDto.getParentId()!=null){
            level=getById(categoryCreateDto.getParentId())
                    .getLevel()+1;
        }

        CategoryEntity categoryEntity = mapStructBlog.toCategoryEntity(categoryCreateDto, level);

        save(categoryEntity);

        B_LOGGER.info("创建分类,{}",categoryEntity.getId());

        return categoryEntity.getId();
    }

    //查看分类
    public CategoryVo getCategory(Long id){
        B_LOGGER.info("查看分类,{}",id);

        CategoryEntity categoryEntity = getById(id);

        if(categoryEntity == null){
            throw new CateGoryNotExist(StatusCode.CATEGORY_NOT_EXIST);
        }

        return mapStructBlog.toCategoryVo(categoryEntity);
    }

    //更新分类
    @PreAuthorize("hasAuthority('allprivilege')")
    @Transactional
    public void updateCategory(CategoryUpdateDto categoryUpdateDto, Long id){
        B_LOGGER.info("更新分类,{}",id);

        if(isNotCategoryExist(id)){
            throw new CateGoryNotExist(StatusCode.CATEGORY_NOT_EXIST);
        }

        if(categoryUpdateDto.getParentId()!=null && isNotCategoryExist(categoryUpdateDto.getParentId())){
            throw new CateGoryNotExist(StatusCode.CATEGORY_NOT_EXIST);
        }

        int level = getById(id).getLevel();

        if(categoryUpdateDto.getParentId()!=null){
            level = getById(categoryUpdateDto.getParentId()).getLevel() + 1;
        }

        lambdaUpdate().eq(CategoryEntity::getId, id)
                .set(StringUtils.hasText(categoryUpdateDto.getName()),CategoryEntity::getName, categoryUpdateDto.getName())
                .set(StringUtils.hasText(categoryUpdateDto.getSlug()),CategoryEntity::getSlug, categoryUpdateDto.getSlug())
                .set(StringUtils.hasText(categoryUpdateDto.getDescription()),CategoryEntity::getDescription, categoryUpdateDto.getDescription())
                .set(categoryUpdateDto.getParentId()!=null,CategoryEntity::getParentId, categoryUpdateDto.getParentId())
                .set(categoryUpdateDto.getParentId()!=null,CategoryEntity::getLevel, level)
                .set(categoryUpdateDto.getSortOrder()!=null,CategoryEntity::getSortOrder, categoryUpdateDto.getSortOrder())
                .update();

        B_LOGGER.info("更新分类,{}",id);
    }

    //删除分类
    @PreAuthorize("hasAuthority('allprivilege')")
    @Transactional
    public void deleteCategory(Long id){
        B_LOGGER.info("删除分类,{}",id);

        if(isNotCategoryExist(id)){
            throw new CateGoryNotExist(StatusCode.CATEGORY_NOT_EXIST);
        }

        removeById(id);

        B_LOGGER.info("删除分类,{}",id);
    }

    //修改分类显示状态
    @Transactional
    public void updateCategoryVisible(Long id, Integer isVisible){
        B_LOGGER.info("修改分类显示状态,{}",id);

        if(isNotCategoryExist(id)){
            throw new CateGoryNotExist(StatusCode.CATEGORY_NOT_EXIST);
        }

        lambdaUpdate().eq(CategoryEntity::getId, id)
                .set(CategoryEntity::getIsVisible, isVisible)
                .update();

        if(Objects.equals(isVisible, Status.CATEGORY_HIDDEN.getCode())){
            B_LOGGER.info("分类隐藏");
        } else{
            B_LOGGER.info("分类显示");
        }
    }

    //显示分类状态
    public Integer showCategoryStatus(Long id){
        B_LOGGER.info("显示分类状态,{}",id);

        CategoryEntity categoryEntity = getById(id);

        if(categoryEntity == null){
            throw new CateGoryNotExist(StatusCode.CATEGORY_NOT_EXIST);
        }

        return categoryEntity.getIsVisible();
    }

    //显示所有分类
    public Page<CategoryAllVo> showAllCategories(Query query){
        B_LOGGER.info("显示所有分类");

        Page<CategoryAllVo> page = new Page<>(query.getPage(), query.getSize());

        return categoryMapper.showAllCategories(page);
    }

    //更新分类文章数量
    public void updateCategoryArticleCount(Long id, Integer articleCount){
        B_LOGGER.info("更新分类文章数量,{}",id);

        if(isNotCategoryExist(id)){
            throw new CateGoryNotExist(StatusCode.CATEGORY_NOT_EXIST);
        }

        lambdaUpdate().eq(CategoryEntity::getId, id)
                .set(CategoryEntity::getArticleCount, articleCount)
                .update();

        B_LOGGER.info("更新分类文章数量成功");
    }

    //查看分类文章数量
    public Integer showCategoryArticleCount(Long id){
        B_LOGGER.info("查看分类文章数量,{}",id);

        CategoryEntity categoryEntity = getById(id);

        if(categoryEntity == null){
            throw new CateGoryNotExist(StatusCode.CATEGORY_NOT_EXIST);
        }

        return categoryEntity.getArticleCount();
    }

    //获取热门分类
    public Page<CategoryAllVo> getHotCategories(HotQuery hotQuery){
        B_LOGGER.info("获取热门分类");

        Page<CategoryAllVo> page = new Page<>(hotQuery.getPage(), hotQuery.getSize());

        return categoryMapper.getHotCategories(page);
    }

    //判断分类是否存在
    public boolean isNotCategoryExist(Long id){
        return !lambdaQuery().eq(CategoryEntity::getId, id)
                .exists();
    }

    public boolean isNotCategoryExist(String name){
        return !lambdaQuery().eq(CategoryEntity::getName, name)
                .exists();
    }
}
