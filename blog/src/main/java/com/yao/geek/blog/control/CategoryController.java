package com.yao.geek.blog.control;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yao.geek.blog.model.dto.CategoryCreateDto;
import com.yao.geek.blog.model.dto.CategoryUpdateDto;
import com.yao.geek.blog.model.query.HotQuery;
import com.yao.geek.blog.model.query.Query;
import com.yao.geek.blog.model.result.Result;
import com.yao.geek.blog.model.vo.CategoryAllVo;
import com.yao.geek.blog.model.vo.CategoryVo;
import com.yao.geek.blog.service.category.CategoryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 分类控制器
 */
@RestController
@RequestMapping("/geek/blog")
@Validated
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    //创建分类
    @PostMapping("/createCategory")
    public Result<Long> createCategory(@RequestBody @Valid CategoryCreateDto categoryCreateDto) {
        return Result.ok(categoryService.createCategory(categoryCreateDto));
    }

    //查看分类
    @GetMapping("/getCategory")
    public Result<CategoryVo> getCategory(@RequestParam("id") @NotNull Long id) {
        return Result.ok(categoryService.getCategory(id));
    }

    //更新分类
    @PutMapping("/updateCategory")
    public Result<Void> updateCategory(@RequestBody @Valid CategoryUpdateDto categoryUpdateDto, @RequestParam("id") @NotNull Long id) {
        categoryService.updateCategory(categoryUpdateDto,id);

        return Result.ok(null);
    }

    //删除分类
    @DeleteMapping("/deleteCategory")
    public Result<Void> deleteCategory(@RequestParam("id") @NotNull Long id) {
        categoryService.deleteCategory(id);

        return Result.ok(null);
    }

    //修改分类显示状态
    @PatchMapping("/updateCategoryVisible")
    public Result<Void> updateCategoryVisible(@RequestParam("id") @NotNull Long id, @RequestParam("isVisible") @NotNull @Min(0) @Max(1)Integer isVisible) {
        categoryService.updateCategoryVisible(id, isVisible);

        return Result.ok(null);
    }

    //显示分类状态
    @GetMapping("/showCategoryStatus")
    public Result<Integer> showCategoryStatus(@RequestParam("id") @NotNull Long id) {
        return Result.ok(categoryService.showCategoryStatus(id));
    }

    //显示所有分类
    @GetMapping("/showAllCategories")
    public Result<Page<CategoryAllVo>> showAllCategories(@ModelAttribute @Valid Query query) {
        return Result.ok(categoryService.showAllCategories(query));
    }

    //更新分类文章数量
    @PutMapping("/updateCategoryArticleCount")
    public Result<Void> updateCategoryArticleCount(@RequestParam("id") @NotNull Long id, @RequestParam("articleCount") @NotNull @Min(0) Integer articleCount) {
        categoryService.updateCategoryArticleCount(id, articleCount);

        return Result.ok(null);
    }

    //查看分类文章数量
    @GetMapping("/showCategoryArticleCount")
    public Result<Integer> showCategoryArticleCount(@RequestParam("id") @NotNull Long id) {
        return Result.ok(categoryService.showCategoryArticleCount(id));
    }

    //获取热门分类
    @GetMapping("/getHotCategories")
    public Result<Page<CategoryAllVo>> getHotCategories(@ModelAttribute @Valid HotQuery hotQuery) {
        return Result.ok(categoryService.getHotCategories(hotQuery));
    }

}
