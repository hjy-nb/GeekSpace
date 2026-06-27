package com.yao.geek.blog.service.article;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yao.geek.blog.common.MapStructBlog;
import com.yao.geek.blog.model.dto.ArticleCreateDto;
import com.yao.geek.blog.model.dto.ArticleUpdateDto;
import com.yao.geek.blog.model.entity.ArticleEntity;
import com.yao.geek.blog.model.query.ArticleQuery;
import com.yao.geek.blog.model.query.HotQuery;
import com.yao.geek.blog.model.query.Query;
import com.yao.geek.blog.model.status.Status;
import com.yao.geek.blog.model.vo.ArticleConditionVo;
import com.yao.geek.blog.model.vo.ArticleVo;
import com.yao.geek.blog.service.article.content.ArticleContentService;
import com.yao.geek.blog.service.category.CategoryService;
import com.yao.geek.blog.service.tag.articletag.ArticleTagService;
import com.yao.geek.common.exception.ArticleIsNotOriginal;
import com.yao.geek.common.exception.ArticleNotExist;
import com.yao.geek.common.exception.CateGoryNotExist;
import com.yao.geek.common.log.GetLogger;
import com.yao.geek.common.status.StatusCode;
import org.slf4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 文章业务逻辑类
 */
@Service
public class ArticleService extends ServiceImpl<ArticleMapper, ArticleEntity> implements ArticleIService {
    //mp方法返回 boolean 是基于插入影响的行数（>0 为 true），而非基于是否发生异常

    private static final Logger B_LOGGER = GetLogger.getBusinessLogger();

    private final MapStructBlog mapStructBlog;
    private final ArticleContentService articleContentService;
    private final ArticleTagService articleTagService;
    private final ArticleMapper articleMapper;
    private final CategoryService categoryService;

    public ArticleService(MapStructBlog mapStructBlog, ArticleContentService articleContentService
             ,ArticleTagService articleTagService,ArticleMapper articleMapper, CategoryService categoryService) {
        this.mapStructBlog = mapStructBlog;
        this.articleContentService = articleContentService;
        this.articleTagService = articleTagService;
        this.articleMapper = articleMapper;
        this.categoryService = categoryService;
    }

    //创建新文章
    @Transactional
    public Long createNewArticle(ArticleCreateDto articleCreateDto, Long userId) {
        B_LOGGER.info("创建新文章,文章标题:{} userId:{}", articleCreateDto.getTitle(), userId);

        if(categoryService.isNotCategoryExist(articleCreateDto.getCategoryId())){
            throw new CateGoryNotExist(StatusCode.CATEGORY_NOT_EXIST);
        }

        ArticleEntity articleEntity = mapStructBlog.toArticleEntity(articleCreateDto, userId);

        save(articleEntity);

        B_LOGGER.info("文章创建成功,下一步判断是否为转载文章");

        if (!articleEntity.getIsOriginal()) {
            B_LOGGER.info("文章为转载文章,不创建默认文章内容");

            return articleEntity.getId();
        }

        B_LOGGER.info("文章为原创文章,创建默认文章内容");

        articleContentService.createNewArticleContent(articleEntity.getId());

        B_LOGGER.info("文章内容创建成功");

        return articleEntity.getId();
    }

    //修改文章状态
    @Transactional
    public void setArticleStatus(Long articleId,Integer status) {
        B_LOGGER.info("修改文章状态,articleId:{}", articleId);

        if(isNotArticleExist(articleId)){
            throw new ArticleNotExist(StatusCode.ARTICLE_NOT_EXIST);
        }

        if(!isOriginal(articleId)){
            throw new ArticleIsNotOriginal(StatusCode.ARTICLE_IS_NOT_ORIGINAL);
        }

        lambdaUpdate().eq(ArticleEntity::getId, articleId)
                .set(ArticleEntity::getStatus, status)
                .set(Objects.equals(status, Status.ARTICLE_PUBLISH.getCode()), ArticleEntity::getPublishTime, LocalDateTime.now())
                .update();

        if(Objects.equals(status, Status.ARTICLE_PUBLISH.getCode())){
            B_LOGGER.info("文章发布成功");
        } else if(Objects.equals(status, Status.ARTICLE_DRAFT.getCode())){
            B_LOGGER.info("文章设为草稿成功");
        } else if(Objects.equals(status, Status.ARTICLE_PRIVATE.getCode())){
            B_LOGGER.info("文章设为私密成功");
        }
    }

    //获取文章状态
    public Integer getArticleStatus(Long articleId){
        B_LOGGER.info("获取文章状态,articleId:{}", articleId);

        ArticleEntity articleEntity = getById(articleId);

        if(articleEntity==null){
            throw new ArticleNotExist(StatusCode.ARTICLE_NOT_EXIST);
        }

        return articleEntity.getStatus();
    }

    //修改文章元数据
    @Transactional
    public void updateArticleMetadata(ArticleUpdateDto articleUpdateDto,Long articleId) {
        B_LOGGER.info("修改文章元数据,articleId:{}", articleId);

        if(isNotArticleExist(articleId)){
            throw new ArticleNotExist(StatusCode.ARTICLE_NOT_EXIST);
        }

        if(!isOriginal(articleId)){
            throw new ArticleIsNotOriginal(StatusCode.ARTICLE_IS_NOT_ORIGINAL);
        }

        lambdaUpdate().eq(ArticleEntity::getId, articleId)
                .set(articleUpdateDto.getTitle()!=null,ArticleEntity::getTitle, articleUpdateDto.getTitle()) //标题
                .set(articleUpdateDto.getSummary()!=null,ArticleEntity::getSummary, articleUpdateDto.getSummary()) //摘要
                .set(articleUpdateDto.getCoverImage()!=null,ArticleEntity::getCoverImage, articleUpdateDto.getCoverImage()) //封面图片
                .set(articleUpdateDto.getCategoryId()!=null,ArticleEntity::getCategoryId, articleUpdateDto.getCategoryId()) //分类id
                .update();

        B_LOGGER.info("文章元数据修改成功");
    }

    //删除文章
    @Transactional
    public void deleteArticle(Long articleId) {
        B_LOGGER.info("删除文章,articleId:{}", articleId);

        if(isNotArticleExist(articleId)){
            throw new ArticleNotExist(StatusCode.ARTICLE_NOT_EXIST);
        }

        removeById(articleId);

        B_LOGGER.info("文章元数据删除成功");

        articleContentService.deleteArticleContent(articleId);

        B_LOGGER.info("文章内容删除成功");

        articleTagService.deleteByArticleId(articleId);

        B_LOGGER.info("文章标签删除成功");

        B_LOGGER.info("文章删除成功");
    }

    //修改阅读数(最终一致)
    @Transactional
    public void updateArticleViewCount(Long articleId, Integer viewCount) {
        B_LOGGER.info("修改文章阅读数,articleId:{},viewCount:{}", articleId, viewCount);

        if(isNotArticleExist(articleId)){
            throw new ArticleNotExist(StatusCode.ARTICLE_NOT_EXIST);
        }

        lambdaUpdate().eq(ArticleEntity::getId, articleId)
                .set(ArticleEntity::getViewCount, viewCount)
                .update();

        B_LOGGER.info("文章阅读数修改成功");
    }

    //获取阅读数
    public Integer getArticleViewCount(Long articleId){
        B_LOGGER.info("获取文章阅读数,articleId:{}", articleId);

        ArticleEntity articleEntity = getById(articleId);

        if(articleEntity==null){
            throw new ArticleNotExist(StatusCode.ARTICLE_NOT_EXIST);
        }

        return articleEntity.getViewCount();
    }

    //修改点赞数(最终一致)
    @Transactional
    public void updateArticleLikeCount(Long articleId, Integer likeCount) {
        B_LOGGER.info("修改文章点赞数,articleId:{},likeCount:{}", articleId, likeCount);

        if(isNotArticleExist(articleId)){
            throw new ArticleNotExist(StatusCode.ARTICLE_NOT_EXIST);
        }

        lambdaUpdate().eq(ArticleEntity::getId, articleId)
                .set(ArticleEntity::getLikeCount, likeCount)
                .update();

        B_LOGGER.info("文章点赞数修改成功");
    }

    //获取点赞数
    public Integer getArticleLikeCount(Long articleId){
        B_LOGGER.info("获取文章点赞数,articleId:{}", articleId);

        ArticleEntity articleEntity = getById(articleId);

        if(articleEntity==null){
            throw new ArticleNotExist(StatusCode.ARTICLE_NOT_EXIST);
        }

        return articleEntity.getLikeCount();
    }

    //修改评论数(最终一致)
    @Transactional
    public void updateArticleCommentCount(Long articleId, Integer commentCount) {
        B_LOGGER.info("修改文章评论数,articleId:{},commentCount:{}", articleId, commentCount);

        if(isNotArticleExist(articleId)){
            throw new ArticleNotExist(StatusCode.ARTICLE_NOT_EXIST);
        }

        lambdaUpdate().eq(ArticleEntity::getId, articleId)
                .set(ArticleEntity::getCommentCount, commentCount)
                .update();

        B_LOGGER.info("文章评论数修改成功");
    }

    //获取评论数
    public Integer getArticleCommentCount(Long articleId){
        B_LOGGER.info("获取文章评论数,articleId:{}", articleId);

        ArticleEntity articleEntity = getById(articleId);

        if(articleEntity==null){
            throw new ArticleNotExist(StatusCode.ARTICLE_NOT_EXIST);
        }

        return articleEntity.getCommentCount();
    }

    //修改分享数(最终一致)
    @Transactional
    public void updateArticleShareCount(Long articleId, Integer shareCount) {
        B_LOGGER.info("修改文章分享数,articleId:{},shareCount:{}", articleId, shareCount);

        if(isNotArticleExist(articleId)){
            throw new ArticleNotExist(StatusCode.ARTICLE_NOT_EXIST);
        }

        lambdaUpdate().eq(ArticleEntity::getId, articleId)
                .set(ArticleEntity::getShareCount, shareCount)
                .update();

        B_LOGGER.info("文章分享数修改成功");
    }

    //获取分享数
    public Integer getArticleShareCount(Long articleId){
        B_LOGGER.info("获取文章分享数,articleId:{}", articleId);

        ArticleEntity articleEntity = getById(articleId);

        if(articleEntity==null){
            throw new ArticleNotExist(StatusCode.ARTICLE_NOT_EXIST);
        }

        return articleEntity.getShareCount();
    }

    //修改置顶状态
    @Transactional
    @PreAuthorize("hasAuthority('allprivilege')")
    public void updateArticleTopStatus(Long articleId, Integer topStatus) {
        B_LOGGER.info("修改文章置顶状态,articleId:{},topStatus:{}", articleId, topStatus);

        if(isNotArticleExist(articleId)){
            throw new ArticleNotExist(StatusCode.ARTICLE_NOT_EXIST);
        }

        lambdaUpdate().eq(ArticleEntity::getId, articleId)
                .set(ArticleEntity::getIsTop, topStatus)
                .update();

        if(Objects.equals(topStatus, Status.ARTICLE_TOP.getCode())){
            B_LOGGER.info("文章设为置顶");
        } else{
            B_LOGGER.info("文章取消置顶");
        }
    }

    //获取置顶状态
    public Boolean getArticleTopStatus(Long articleId){
        B_LOGGER.info("获取文章置顶状态,articleId:{}", articleId);

        ArticleEntity articleEntity = getById(articleId);

        if(articleEntity==null){
            throw new ArticleNotExist(StatusCode.ARTICLE_NOT_EXIST);
        }

        return articleEntity.getIsTop();
    }

    //获取文章元数据
    public ArticleVo getArticleMetadata(Long articleId) {
        B_LOGGER.info("获取文章元数据,articleId:{}", articleId);

        ArticleEntity articleEntity=getById(articleId);

        if(articleEntity==null){
            throw new ArticleNotExist(StatusCode.ARTICLE_NOT_EXIST);
        }

        return mapStructBlog.toArticleVo(articleEntity);
    }

    //条件查询文章
    public Page<ArticleConditionVo> getArticleList(ArticleQuery articleQuery){
        B_LOGGER.info("条件查询文章");

        Page<ArticleConditionVo> page=new Page<>(articleQuery.getPage(), articleQuery.getSize());

        return articleMapper.getArticleList(page,articleQuery);
    }

    //查看热点文章
    public Page<ArticleConditionVo> getHotArticleList(HotQuery hotQuery){
        B_LOGGER.info("查看热点文章");

        Page<ArticleConditionVo> page=new Page<>(hotQuery.getPage(), hotQuery.getSize());

        return articleMapper.getHotArticleList(page);
    }

    //查看分类下所有文章
    public Page<ArticleConditionVo> getArticleListByCategory(Long categoryId, Query query){
        B_LOGGER.info("查看分类下所有文章,categoryId:{}", categoryId);

        if(categoryService.isNotCategoryExist(categoryId)){
            throw new CateGoryNotExist(StatusCode.CATEGORY_NOT_EXIST);
        }

        Page<ArticleConditionVo> page=new Page<>(query.getPage(), query.getSize());

        return articleMapper.getArticleListByCategory(page,categoryId);
    }

    //判断是否为原创
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean isOriginal(Long articleId) {
        return lambdaQuery()
                .eq(ArticleEntity::getId, articleId)
                .select(ArticleEntity::getIsOriginal)
                .one()
                .getIsOriginal();
    }

    //判断文章是否不存在
    public boolean isNotArticleExist(Long articleId) {
        return !lambdaQuery()
                .eq(ArticleEntity::getId, articleId)
                .exists();
    }
}
