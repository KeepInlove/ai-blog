package com.ai.blog.controller.admin;

import com.ai.blog.annotation.OptLog;
import com.ai.blog.dto.CategoryBackDTO;
import com.ai.blog.dto.CategoryOptionDTO;
import com.ai.blog.service.CategoryService;
import com.ai.blog.vo.CategoryVO;
import com.ai.blog.vo.ConditionVO;
import com.ai.blog.vo.PageResult;
import com.ai.blog.vo.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.ai.blog.constant.OptTypeConst.REMOVE;
import static com.ai.blog.constant.OptTypeConst.SAVE_OR_UPDATE;

/**
 * @author 菜队
 * @description: 分类控制器
 * @date 2025-03-06 21:00
 * @email gxy06x@qq.com
 */
@Tag(name = "分类模块")
@RestController
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    /**
     * 查看后台分类列表
     *
     * @param condition 条件
     * @return {@link Result < CategoryBackDTO >} 后台分类列表
     */
    @Operation(summary = "查看后台分类列表")
    @GetMapping("/admin/categories")
    public Result<PageResult<CategoryBackDTO>> listBackCategories(ConditionVO condition) {
        return Result.ok(categoryService.listBackCategories(condition));
    }

    /**
     * 搜索文章分类
     *
     * @param condition 条件
     * @return {@link Result< CategoryOptionDTO >} 分类列表
     */
    @Operation(summary = "搜索文章分类")
    @GetMapping("/admin/categories/search")
    public Result<List<CategoryOptionDTO>> listCategoriesBySearch(ConditionVO condition) {
        return Result.ok(categoryService.listCategoriesBySearch(condition));
    }

    /**
     * 添加或修改分类
     *
     * @param categoryVO 分类信息
     * @return {@link Result<>}
     */
    @OptLog(optType = SAVE_OR_UPDATE)
    @Operation(summary = "添加或修改分类")
    @PostMapping("/admin/categories")
    public Result<?> saveOrUpdateCategory(@Valid @RequestBody CategoryVO categoryVO) {
        categoryService.saveOrUpdateCategory(categoryVO);
        return Result.ok();
    }

    /**
     * 删除分类
     *
     * @param categoryIdList 分类id列表
     * @return {@link Result<>}
     */
    @OptLog(optType = REMOVE)
    @Operation(summary = "删除分类")
    @DeleteMapping("/admin/categories")
    public Result<?> deleteCategories(@RequestBody List<Integer> categoryIdList) {
        categoryService.deleteCategory(categoryIdList);
        return Result.ok();
    }
}
