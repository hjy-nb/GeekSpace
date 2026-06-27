package com.yao.geek.blog.control;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yao.geek.blog.model.dto.ArticleTagDto;
import com.yao.geek.blog.model.dto.TagCreateDto;
import com.yao.geek.blog.model.dto.TagUpdateDto;
import com.yao.geek.blog.model.query.ArticleTagQuery;
import com.yao.geek.blog.model.query.HotQuery;
import com.yao.geek.blog.model.query.Query;
import com.yao.geek.blog.model.result.Result;
import com.yao.geek.blog.model.vo.TagAllVo;
import com.yao.geek.blog.model.vo.TagVo;
import com.yao.geek.blog.service.tag.TagService;
import com.yao.geek.blog.service.tag.articletag.ArticleTagService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 标签控制器
 */
@RestController
@RequestMapping("/geek/blog")
@Validated
public class TagController {
    private final TagService tagService;
    private final ArticleTagService articleTagService;

    public TagController(TagService tagService,ArticleTagService articleTagService) {
        this.tagService = tagService;
        this.articleTagService = articleTagService;
    }

    //增加标签
    @PostMapping("/addTag")
    public Result<Long> addTag(@RequestBody @Valid TagCreateDto tagCreateDto) {
        return Result.ok(tagService.addTag(tagCreateDto));
    }

    //修改标签
    @PutMapping("/updateTag")
    public Result<Void> updateTag(@RequestParam("id") @NotNull Long id,@RequestBody @Valid TagUpdateDto tagUpdateDto) {
        tagService.updateTag(id,tagUpdateDto);

        return Result.ok(null);
    }

    //删除标签
    @DeleteMapping("/deleteTag")
    public Result<Void> deleteTag(@RequestParam("id") @NotNull Long id) {
        tagService.deleteTag(id);

        return Result.ok(null);
    }

    //查询标签
    @GetMapping("/getTag")
    public Result<TagVo> getTag(@RequestParam("id") @NotNull Long id) {
        return Result.ok(tagService.getTag(id));
    }

    //查询所有标签
    @GetMapping("/getAllTags")
    public Result<Page<TagAllVo>> getAllTags(@ModelAttribute @Valid Query query) {
        return Result.ok(tagService.getAllTags(query));
    }

    //更新文章数量
    @PatchMapping("/updateArticleCount")
    public Result<Void> updateArticleCount(@RequestParam("id") @NotNull Long id, @RequestParam("count") @NotNull @Min(0) Integer count) {
        tagService.updateArticleCount(id, count);

        return Result.ok(null);
    }

    //获取文章数量
    @GetMapping("/getArticleCount")
    public Result<Integer> getArticleCount(@RequestParam("id") @NotNull Long id){
        return Result.ok(tagService.getArticleCount(id));
    }

    //获取热门标签
    @GetMapping("/getHotTags")
    public Result<Page<TagAllVo>> getHotTags(@ModelAttribute @Valid HotQuery query) {
        return Result.ok(tagService.getHotTags(query));
    }

    //获取文章所有标签
    @GetMapping("/getTagAllVoByArticleId")
    public Result<Page<Long>> getTagAllVoByArticleId(@ModelAttribute @Valid ArticleTagQuery articleTagQuery) {
        return Result.ok(articleTagService.getTagAllVoByArticleId(articleTagQuery));
    }

    //查询标签所有文章
    @GetMapping("/getArticleAllVoByTagId")
    public Result<Page<Long>> getArticleAllVoByTagId(@ModelAttribute @Valid ArticleTagQuery articleTagQuery) {
        return Result.ok(articleTagService.getArticleAllVoByTagId(articleTagQuery));
    }

    //创建文章标签映射
    @PostMapping("/createArticleTag")
    public Result<List<Long>> createArticleTag(@RequestBody @Valid ArticleTagDto articleTagDto) {
        return Result.ok(articleTagService.createArticleTag(articleTagDto));
    }
}
