package com.ai.blog.controller.admin;

import com.ai.blog.dto.TalkBackDTO;
import com.ai.blog.dto.TalkDTO;
import com.ai.blog.enums.FilePathEnum;
import com.ai.blog.service.TalkService;
import com.ai.blog.strategy.context.UploadStrategyContext;
import com.ai.blog.vo.ConditionVO;
import com.ai.blog.vo.PageResult;
import com.ai.blog.vo.Result;
import com.ai.blog.vo.TalkVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author Guoxinyu
 * @description: TODO
 * @date 2025-03-06 21:58
 * @email gxy06x@qq.com
 */
@Tag(name = "说说模块")
@RestController
public class TalkController {
    @Resource
    private TalkService talkService;
    @Resource
    private UploadStrategyContext uploadStrategyContext;

    /**
     * 上传说说图片
     *
     * @param file 文件
     * @return {@link Result <String>} 说说图片地址
     */
    @Operation(summary = "上传说说图片")
    @Parameter(name = "file", description = "说说图片", required = true, example = "MultipartFile")
    @PostMapping("/admin/talks/images")
    public Result<String> saveTalkImages(MultipartFile file) {
        return Result.ok(uploadStrategyContext.executeUploadStrategy(file, FilePathEnum.TALK.getPath()));
    }

    /**
     * 保存或修改说说
     *
     * @param talkVO 说说信息
     * @return {@link Result<>}
     */
    @Operation(summary = "保存或修改说说")
    @PostMapping("/admin/talks")
    public Result<?> saveOrUpdateTalk(@Valid @RequestBody TalkVO talkVO) {
        talkService.saveOrUpdateTalk(talkVO);
        return Result.ok();
    }

    /**
     * 删除说说
     *
     * @param talkIdList 说说id列表
     * @return {@link Result<>}
     */
    @Operation(summary = "删除说说")
    @DeleteMapping("/admin/talks")
    public Result<?> deleteTalks(@RequestBody List<Integer> talkIdList) {
        talkService.deleteTalks(talkIdList);
        return Result.ok();
    }

    /**
     * 查看后台说说
     *
     * @param conditionVO 条件
     * @return {@link Result< TalkBackDTO >} 说说列表
     */
    @Operation(summary = "查看后台说说")
    @GetMapping("/admin/talks")
    public Result<PageResult<TalkBackDTO>> listBackTalks(ConditionVO conditionVO) {
        return Result.ok(talkService.listBackTalks(conditionVO));
    }

    /**
     * 根据id查看后台说说
     *
     * @param talkId 说说id
     * @return {@link Result< TalkDTO >}
     */
    @Operation(summary = "根据id查看后台说说")
    @Parameter(name = "talkId", description = "说说id", required = true, example = "Integer")
    @GetMapping("/admin/talks/{talkId}")
    public Result<TalkBackDTO> getBackTalkById(@PathVariable("talkId") Integer talkId) {
        return Result.ok(talkService.getBackTalkById(talkId));
    }


}
