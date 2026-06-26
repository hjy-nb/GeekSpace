package com.yao.geek.blog.service.tag.articletag;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yao.geek.blog.model.entity.ArticleTagEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ArticleTagService extends ServiceImpl<ArticleTagMapper, ArticleTagEntity> implements ArticleTagIService {
    //删除文章所有标签
    @Transactional
    public void deleteByArticleId(Long articleId) {
        remove(Wrappers.lambdaQuery(ArticleTagEntity.class)
                .eq(ArticleTagEntity::getArticleId, articleId));
    }
}
