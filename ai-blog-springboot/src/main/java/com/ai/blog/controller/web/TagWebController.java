package com.ai.blog.controller.web;


import com.ai.blog.dto.TagDTO;
import com.ai.blog.service.TagService;
import com.ai.blog.vo.PageResult;
import com.ai.blog.vo.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 标签控制器
 *
 * @author yezhiqiu
 * @date 2021/07/29
 */
@Tag(name = "标签模块")
@RestController
public class TagWebController {
    @Autowired
    private TagService tagService;

    /**
     * 查询标签列表
     *
     * @return {@link Result <TagDTO>} 标签列表
     */
    @Operation(summary = "查询标签列表")
    @GetMapping("/tags")
    public Result<PageResult<TagDTO>> listTags() {
        return Result.ok(tagService.listTags());
    }



}

