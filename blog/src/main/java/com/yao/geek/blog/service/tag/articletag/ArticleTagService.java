package com.yao.geek.blog.service.tag.articletag;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yao.geek.blog.common.MapStructBlog;
import com.yao.geek.blog.model.dto.ArticleTagDto;
import com.yao.geek.blog.model.entity.ArticleTagEntity;
import com.yao.geek.blog.model.query.ArticleTagQuery;
import com.yao.geek.blog.model.query.Query;
import com.yao.geek.blog.model.vo.TagAllVo;
import com.yao.geek.blog.service.article.ArticleService;
import com.yao.geek.blog.service.tag.TagService;
import com.yao.geek.common.exception.ArticleIdIsNull;
import com.yao.geek.common.exception.ArticleNotExist;
import com.yao.geek.common.exception.TagIdIsNull;
import com.yao.geek.common.exception.TagNotExist;
import com.yao.geek.common.log.GetLogger;
import com.yao.geek.common.status.StatusCode;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class ArticleTagService extends ServiceImpl<ArticleTagMapper, ArticleTagEntity> implements ArticleTagIService {
    private static final Logger B_LOGGER=GetLogger.getBusinessLogger();

    private final ArticleTagMapper articleTagMapper;
    private final TagService tagService;
    private final ArticleService articleService;
    private final MapStructBlog mapStructBlog;

    public ArticleTagService(ArticleTagMapper articleTagMapper, TagService tagService,ArticleService articleService,
                             MapStructBlog mapStructBlog) {
        this.articleTagMapper = articleTagMapper;
        this.tagService = tagService;
        this.articleService = articleService;
        this.mapStructBlog = mapStructBlog;
    }

    //创建文章标签映射
    @Transactional
    public List<Long> createArticleTag(ArticleTagDto articleTagDto) {
        B_LOGGER.info("创建文章标签映射,articleId:{},tagIds:{}", articleTagDto.getArticleId(), articleTagDto.getTagIds());

        if(articleService.isNotArticleExist(articleTagDto.getArticleId())){
            throw new ArticleNotExist(StatusCode.ARTICLE_NOT_EXIST);
        }

        if(tagService.isNotTagExist(articleTagDto.getTagIds())){
            throw new TagNotExist(StatusCode.TAG_NOT_EXIST);
        }

        List<ArticleTagEntity> articleTagEntity = articleTagDto.getTagIds()
                .stream()
                .map(tagId -> mapStructBlog.toArticleTagEntity(articleTagDto.getArticleId(), tagId))
                .toList();


        saveBatch(articleTagEntity);

        B_LOGGER.info("创建成功");

        return articleTagEntity.stream()
                .map(ArticleTagEntity::getId)
                .toList();
    }

    //删除文章所有标签
    @Transactional
    public void deleteByArticleId(Long articleId) {
        remove(Wrappers.lambdaQuery(ArticleTagEntity.class)
                .eq(ArticleTagEntity::getArticleId, articleId));
    }

    //删除标签所有文章
    @Transactional
    public void deleteByTagId(Long tagId) {
        remove(Wrappers.lambdaQuery(ArticleTagEntity.class)
                .eq(ArticleTagEntity::getTagId, tagId));
    }

    //查询文章所有标签
    public Page<Long> getTagAllVoByArticleId(ArticleTagQuery articleTagQuery) {
        if(articleTagQuery.getArticleId()==null){
            throw new ArticleIdIsNull(StatusCode.ARTICLE_ID_NULL);
        }

        B_LOGGER.info("查询文章所有标签,articleId:{}",articleTagQuery.getArticleId());

        Page<Long> page = new Page<>(articleTagQuery.getPage(), articleTagQuery.getSize());

        return articleTagMapper.getTagAllVoByArticleId(page, articleTagQuery.getArticleId());
    }

    //查询标签所有文章
    public Page<Long> getArticleAllVoByTagId(ArticleTagQuery articleTagQuery) {
        if(articleTagQuery.getTagId()==null){
            throw new TagIdIsNull(StatusCode.TAG_ID_NULL);
        }

        B_LOGGER.info("查询标签所有文章,tagId:{}",articleTagQuery.getTagId());

        Page<Long> page = new Page<>(articleTagQuery.getPage(), articleTagQuery.getSize());

        return articleTagMapper.getArticleAllVoByTagId(page, articleTagQuery.getTagId());
    }
}
