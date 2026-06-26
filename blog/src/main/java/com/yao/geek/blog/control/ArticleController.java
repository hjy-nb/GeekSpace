package com.yao.geek.blog.control;

import com.yao.geek.blog.model.dto.ArticleContentUpdateDto;
import com.yao.geek.blog.model.dto.ArticleCreateDto;
import com.yao.geek.blog.model.dto.ArticleUpdateDto;
import com.yao.geek.blog.model.query.ArticleQuery;
import com.yao.geek.blog.model.result.Result;
import com.yao.geek.blog.model.vo.ArticleConditionVo;
import com.yao.geek.blog.model.vo.ArticleVo;
import com.yao.geek.blog.service.article.ArticleService;
import com.yao.geek.blog.service.article.content.ArticleContentService;
import com.yao.geek.common.Constant.NumConstant;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * 文章控制层
 */
@RestController
@RequestMapping("/geek/blog")
@Validated
public class ArticleController {
    private final ArticleService articleService;
    private final ArticleContentService articleContentService;

    public ArticleController(ArticleService articleService, ArticleContentService articleContentService) {
        this.articleService = articleService;
        this.articleContentService = articleContentService;
    }

    //创建新文章
    @PostMapping("/create")
    public Result<Long> createNewArticle(@RequestBody @Valid ArticleCreateDto articleCreateDto,
                                      @RequestHeader(NumConstant.T_ID) Long userId){
        return Result.ok(articleService.createNewArticle(articleCreateDto, userId));
    }

    //修改文章内容(put是全量更新(几乎是全量更新也用put)，patch是部分更新)
    @PutMapping("/updateContent")
    public Result<?> updateArticleContent(@RequestBody @Valid ArticleContentUpdateDto articleContentUpdateDto) throws NoSuchAlgorithmException {
        articleContentService.updateArticleContent(articleContentUpdateDto);

        return Result.ok(null);
    }

    //修改文章状态
    @PatchMapping("/setArticleStatus")
    public Result<?> setArticleStatus(@RequestParam("id") @NotNull Long articleId,
                                      @RequestParam("status") @NotNull @Min(0) @Max(2) Integer status) {
        articleService.setArticleStatus(articleId, status);

        return Result.ok(null);
    }

    //修改文章元数据
    @PutMapping("/updateMetadata")
    public Result<?> updateArticleMetadata(@RequestBody @Valid ArticleUpdateDto articleUpdateDto,
                                           @RequestParam("id") @NotNull Long articleId) {
        articleService.updateArticleMetadata(articleUpdateDto, articleId);

        return Result.ok(null);
    }

    //增加阅读数
    @PatchMapping("/updateViewCount")
    public Result<?> updateArticleViewCount(@RequestParam("id") @NotNull Long articleId,
                                            @RequestParam("viewCount") @NotNull @Min(0) Integer viewCount) {
        articleService.updateArticleViewCount(articleId, viewCount);

        return Result.ok(null);
    }

    //增加点赞数
    @PatchMapping("/updateLikeCount")
    public Result<?> updateArticleLikeCount(@RequestParam("id") @NotNull Long articleId,
                                            @RequestParam("likeCount") @NotNull @Min(0) Integer likeCount) {
        articleService.updateArticleLikeCount(articleId, likeCount);

        return Result.ok(null);
    }

    //增加评论数
    @PatchMapping("/updateCommentCount")
    public Result<?> updateArticleCommentCount(@RequestParam("id") @NotNull Long articleId,
                                               @RequestParam("commentCount") @NotNull @Min(0) Integer commentCount) {
        articleService.updateArticleCommentCount(articleId, commentCount);

        return Result.ok(null);
    }

    //增加分享数
    @PatchMapping("/updateShareCount")
    public Result<?> updateArticleShareCount(@RequestParam("id") @NotNull Long articleId,
                                             @RequestParam("shareCount") @NotNull @Min(0) Integer shareCount) {
        articleService.updateArticleShareCount(articleId, shareCount);

        return Result.ok(null);
    }

    //修改文章置顶状态
    @PatchMapping("/updateTopStatus")
    public Result<?> updateArticleTopStatus(@RequestParam("id") @NotNull Long articleId,
                                            @RequestParam("topStatus") @NotNull @Min(0) @Max(1) Integer topStatus) {
        articleService.updateArticleTopStatus(articleId, topStatus);

        return Result.ok(null);
    }

    //删除文章
    @DeleteMapping("/delete")
    public Result<?> deleteArticle(@RequestParam("id") @NotNull Long articleId){
        articleService.deleteArticle(articleId);

        return Result.ok(null);
    }

    //获取文章内容
    @GetMapping("/getContent")
    public Result<?> getArticleContent(@RequestParam("id") @NotNull Long articleId){
        return Result.ok(articleContentService.getArticleContent(articleId));
    }

    //获取文章元数据
    @GetMapping("/getMetadata")
    public Result<ArticleVo> getArticleMetadata(@RequestParam("id") @NotNull Long articleId){
        return Result.ok(articleService.getArticleMetadata(articleId));
    }

    //条件查询文章
    @GetMapping("/getArticleList")
    public Result<List<ArticleConditionVo>> getArticleList(@ModelAttribute @Valid ArticleQuery articleQuery){

    }
}
