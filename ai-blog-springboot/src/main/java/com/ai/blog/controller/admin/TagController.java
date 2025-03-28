package com.ai.blog.controller.admin;

import com.ai.blog.annotation.OptLog;
import com.ai.blog.dto.TagBackDTO;
import com.ai.blog.dto.TagDTO;
import com.ai.blog.service.TagService;
import com.ai.blog.vo.ConditionVO;
import com.ai.blog.vo.PageResult;
import com.ai.blog.vo.Result;
import com.ai.blog.vo.TagVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.ai.blog.constant.OptTypeConst.REMOVE;
import static com.ai.blog.constant.OptTypeConst.SAVE_OR_UPDATE;

/**
 * @author 菜队
 * @description: TODO
 * @date 2025-03-06 21:57
 * @email gxy06x@qq.com
 */
@Tag(name = "标签模块")
@RestController
public class TagController {
    @Resource
    private TagService tagService;
    /**
     * 查询后台标签列表
     *
     * @param condition 条件
     * @return {@link Result < TagBackDTO >} 标签列表
     */
    @Operation(summary = "查询后台标签列表")
    @GetMapping("/admin/tags")
    public Result<PageResult<TagBackDTO>> listTagBackDTO(ConditionVO condition) {
        return Result.ok(tagService.listTagBackDTO(condition));
    }

    /**
     * 搜索文章标签
     *
     * @param condition 条件
     * @return {@link Result<String>} 标签列表
     */
    @Operation(summary = "搜索文章标签")
    @GetMapping("/admin/tags/search")
    public Result<List<TagDTO>> listTagsBySearch(ConditionVO condition) {
        return Result.ok(tagService.listTagsBySearch(condition));
    }

    /**
     * 添加或修改标签
     *
     * @param tagVO 标签信息
     * @return {@link Result<>}
     */
    @OptLog(optType = SAVE_OR_UPDATE)
    @Operation(summary = "添加或修改标签")
    @PostMapping("/admin/tags")
    public Result<?> saveOrUpdateTag(@Valid @RequestBody TagVO tagVO) {
        tagService.saveOrUpdateTag(tagVO);
        return Result.ok();
    }

    /**
     * 删除标签
     *
     * @param tagIdList 标签id列表
     * @return {@link Result<>}
     */
    @OptLog(optType = REMOVE)
    @Operation(summary = "删除标签")
    @DeleteMapping("/admin/tags")
    public Result<?> deleteTag(@RequestBody List<Integer> tagIdList) {
        tagService.deleteTag(tagIdList);
        return Result.ok();
    }
}
