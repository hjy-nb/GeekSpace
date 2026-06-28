package com.yao.geek.blog.service.article.content;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.houbb.sensitive.word.bs.SensitiveWordBs;
import com.yao.geek.blog.common.MapStructBlog;
import com.yao.geek.blog.model.dto.ArticleContentUpdateDto;
import com.yao.geek.blog.model.entity.ArticleContentEntity;
import com.yao.geek.blog.model.vo.ArticleContentVo;
import com.yao.geek.blog.service.article.ArticleService;
import com.yao.geek.common.exception.ArticleExist;
import com.yao.geek.common.exception.ArticleIsNotOriginal;
import com.yao.geek.common.exception.ArticleNotExist;
import com.yao.geek.common.log.GetLogger;
import com.yao.geek.common.sha256.GetHashSha256;
import com.yao.geek.common.status.StatusCode;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;

/**
 * 文章内容业务逻辑类
 */
@Service
public class ArticleContentService extends ServiceImpl<ArticleContentMapper, ArticleContentEntity> implements ArticleContentIService {
    private static final Logger B_LOGGER = GetLogger.getBusinessLogger();

    private final ArticleService articleService;
    private final MapStructBlog mapStructBlog;
    private final SensitiveWordBs sensitiveWordBs = SensitiveWordBs.newInstance();

    public ArticleContentService(ArticleService articleService, MapStructBlog mapStructBlog) {
        this.articleService = articleService;
        this.mapStructBlog = mapStructBlog;
    }

    //创建新文章内容
    @Transactional
    public void createNewArticleContent(Long articleId) {
        save(ArticleContentEntity.builder()
                .id(articleId)
                .build());
    }

    //修改文章内容(判断是否包含敏感词)
    @Transactional
    public void updateArticleContent(ArticleContentUpdateDto articleContentUpdateDto) throws NoSuchAlgorithmException{
        B_LOGGER.info("修改文章内容,articleId:{}", articleContentUpdateDto.getId());

        if(articleService.isNotArticleExist(articleContentUpdateDto.getId())){
            throw new ArticleNotExist(StatusCode.ARTICLE_NOT_EXIST);
        }

        if(!articleService.isOriginal(articleContentUpdateDto.getId())){
            throw new ArticleIsNotOriginal(StatusCode.ARTICLE_IS_NOT_ORIGINAL);
        }

        if(sensitiveWordBs.contains(articleContentUpdateDto.getContent())){
            B_LOGGER.info("文章内容包含敏感词待审核,{}", articleContentUpdateDto.getId());

            //审核，审核完才能修改
        }

        B_LOGGER.info("获取文章内容的哈希值");

        String contentHash = GetHashSha256.getHashSha256(articleContentUpdateDto.getContent());

        if(isArticleContentExist(contentHash)){
            throw new ArticleExist(StatusCode.ARTICLE_EXIST);
        }

        lambdaUpdate().eq(ArticleContentEntity::getId, articleContentUpdateDto.getId())
                .set(ArticleContentEntity::getContent, articleContentUpdateDto.getContent())
                .set(ArticleContentEntity::getContentHash, contentHash)
                .set(articleContentUpdateDto.getMarkdownContent()!=null,ArticleContentEntity::getMarkdownContent, articleContentUpdateDto.getMarkdownContent())
                .set(ArticleContentEntity::getWordCount, articleContentUpdateDto.getContent().length())
                .update();

        B_LOGGER.info("修改文章内容成功,articleId:{}", articleContentUpdateDto.getId());
    }

    //删除文章内容
    @Transactional
    public void deleteArticleContent(Long articleId) {
        removeById(articleId);
    }

    //获取文章内容
    public ArticleContentVo getArticleContent(Long articleId){
        B_LOGGER.info("获取文章内容,articleId:{}", articleId);

        ArticleContentEntity articleContentEntity = getById(articleId);

        if (articleContentEntity == null) {
            throw new ArticleNotExist(StatusCode.ARTICLE_NOT_EXIST);
        }

        return mapStructBlog.toArticleContentVo(articleContentEntity);
    }

    //判断文章内容是否已存在
    public boolean isArticleContentExist(String contentHash) {
        return lambdaQuery()
                .eq(ArticleContentEntity::getContentHash, contentHash)
                .exists();
    }
}
