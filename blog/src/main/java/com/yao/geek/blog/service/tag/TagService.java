package com.yao.geek.blog.service.tag;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yao.geek.blog.common.MapStructBlog;
import com.yao.geek.blog.model.dto.TagCreateDto;
import com.yao.geek.blog.model.dto.TagUpdateDto;
import com.yao.geek.blog.model.entity.TagEntity;
import com.yao.geek.blog.model.query.HotQuery;
import com.yao.geek.blog.model.query.Query;
import com.yao.geek.blog.model.vo.TagAllVo;
import com.yao.geek.blog.model.vo.TagVo;
import com.yao.geek.blog.service.tag.articletag.ArticleTagService;
import com.yao.geek.common.exception.TagExist;
import com.yao.geek.common.exception.TagNotExist;
import com.yao.geek.common.log.GetLogger;
import com.yao.geek.common.status.StatusCode;
import org.slf4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 标签服务类
 */
@Service
public class TagService extends ServiceImpl<TagMapper, TagEntity> implements TagIService {
    private static final Logger B_LOGGER= GetLogger.getBusinessLogger();

    private final MapStructBlog mapStructBlog;
    private final ArticleTagService articleTagService;
    private final TagMapper tagMapper;

    public TagService(MapStructBlog mapStructBlog,ArticleTagService articleTagService,TagMapper tagMapper) {
        this.mapStructBlog = mapStructBlog;
        this.articleTagService = articleTagService;
        this.tagMapper = tagMapper;
    }

    //增加标签
    @PreAuthorize("hasAuthority('allprivilege')")
    @Transactional
    public Long addTag(TagCreateDto tagCreateDto){
        B_LOGGER.info("添加标签，{}", tagCreateDto.getName());

        if(!isNotTagExist(tagCreateDto.getName())){
            throw new TagExist(StatusCode.TAG_EXIST);
        }

        TagEntity tagEntity = mapStructBlog.toTagEntity(tagCreateDto);

        save(tagEntity);

        B_LOGGER.info("添加标签成功，{}", tagEntity.getId());

        return tagEntity.getId();
    }

    //修改标签
    @PreAuthorize("hasAuthority('allprivilege')")
    @Transactional
    public void updateTag(Long id, TagUpdateDto tagUpdateDto){
        B_LOGGER.info("修改标签，{}", id);

        if(isNotTagExist(id)){
            throw new TagNotExist(StatusCode.TAG_NOT_EXIST);
        }

        lambdaUpdate().eq(TagEntity::getId, id)
                .set(StringUtils.hasText(tagUpdateDto.getName()),TagEntity::getName, tagUpdateDto.getName())
                .set(StringUtils.hasText(tagUpdateDto.getSlug()),TagEntity::getSlug, tagUpdateDto.getSlug())
                .set(StringUtils.hasText(tagUpdateDto.getDescription()),TagEntity::getDescription, tagUpdateDto.getDescription())
                .update();

        B_LOGGER.info("修改标签成功");
    }

    //删除标签
    @PreAuthorize("hasAuthority('allprivilege')")
    @Transactional
    public void deleteTag(Long id){
        B_LOGGER.info("删除标签，{}", id);

        if(isNotTagExist(id)){
            throw new TagNotExist(StatusCode.TAG_NOT_EXIST);
        }

        B_LOGGER.info("删除标签与文章关联");

        articleTagService.deleteByTagId(id);

        B_LOGGER.info("删除标签与文章关联成功");

        removeById(id);

        B_LOGGER.info("删除标签成功");
    }

    //查询标签
    public TagVo getTag(Long id){
        B_LOGGER.info("查询标签，{}", id);

        TagEntity tagEntity = getById(id);

        if (tagEntity == null) {
            throw new TagNotExist(StatusCode.TAG_NOT_EXIST);
        }

        B_LOGGER.info("查询标签成功");

        return mapStructBlog.toTagVo(tagEntity);
    }

    //查询所有标签
    public Page<TagAllVo> getAllTags(Query query){
        B_LOGGER.info("查询所有标签");

        Page<TagAllVo> page=new Page<>(query.getPage(), query.getSize());

        return tagMapper.getAllTags(page);
    }

    //更新文章数量
    @Transactional
    public void updateArticleCount(Long id, Integer articleCount){
        B_LOGGER.info("更新标签文章数量，{}", id);

        if(!isNotTagExist(id)){
            throw new TagNotExist(StatusCode.TAG_NOT_EXIST);
        }

        lambdaUpdate().eq(TagEntity::getId, id)
                .set(TagEntity::getArticleCount, articleCount)
                .update();

        B_LOGGER.info("更新标签文章数量成功");
    }

    //获取文章数量
    public Integer getArticleCount(Long id){
        B_LOGGER.info("获取标签文章数量，{}", id);

        TagEntity tagEntity = getById(id);

        if(tagEntity == null){
            throw new TagNotExist(StatusCode.TAG_NOT_EXIST);
        }

        return tagEntity.getArticleCount();
    }

    //获取热门标签
    public Page<TagAllVo> getHotTags(HotQuery hotQuery){
        B_LOGGER.info("查询热门标签");

        Page<TagAllVo> page=new Page<>(hotQuery.getPage(), hotQuery.getSize());

        return tagMapper.getHotTags(page);
    }


    //判断标签是否存在
    public boolean isNotTagExist(Long id){
        return !lambdaQuery()
                .eq(TagEntity::getId, id)
                .exists();
    }

    public boolean isNotTagExist(String name){
        return !lambdaQuery()
                .eq(TagEntity::getName, name)
                .exists();
    }

    //批量判断标签是否都存在
    public boolean isNotTagExist(List<Long> ids){
        long  num= lambdaQuery()
                .in(TagEntity::getId, ids)
                .count();

        return num != ids.size();
    }
}
