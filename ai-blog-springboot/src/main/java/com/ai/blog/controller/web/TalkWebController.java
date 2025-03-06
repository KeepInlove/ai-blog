package com.ai.blog.controller.web;

import com.ai.blog.dto.TalkDTO;
import com.ai.blog.service.TalkService;
import com.ai.blog.vo.PageResult;
import com.ai.blog.vo.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 说说控制器
 *
 */
@Tag(name = "说说模块")
@RestController
public class TalkWebController {
    @Resource
    private TalkService talkService;

    /**
     * 查看首页说说
     *
     * @return {@link Result<String>}
     */
    @Operation(summary = "查看首页说说")
    @GetMapping("/home/talks")
    public Result<List<String>> listHomeTalks() {
        return Result.ok(talkService.listHomeTalks());
    }

    /**
     * 查看说说列表
     *
     * @return {@link Result<TalkDTO>}
     */
    @Operation(summary = "查看说说列表")
    @GetMapping("/talks")
    public Result<PageResult<TalkDTO>> listTalks() {
        return Result.ok(talkService.listTalks());
    }

    /**
     * 根据id查看说说
     *
     * @param talkId 说说id
     * @return {@link Result<TalkDTO>}
     */
    @Operation(summary = "根据id查看说说")
    @Parameter(name = "talkId", description = "说说id", required = true, example = "Integer")
    @GetMapping("/talks/{talkId}")
    public Result<TalkDTO> getTalkById(@PathVariable("talkId") Integer talkId) {
        return Result.ok(talkService.getTalkById(talkId));
    }

    /**
     * 点赞说说
     *
     * @param talkId 说说id
     * @return {@link Result<>}
     */
    @Operation(summary = "点赞说说")
    @Parameter(name = "talkId", description = "说说id", required = true, example = "Integer")
    @PostMapping("/talks/{talkId}/like")
    public Result<?> saveTalkLike(@PathVariable("talkId") Integer talkId) {
        talkService.saveTalkLike(talkId);
        return Result.ok();
    }


}
