package com.ai.blog.controller.web;


import com.ai.blog.dto.BlogHomeInfoDTO;
import com.ai.blog.service.BlogInfoService;
import com.ai.blog.service.impl.WebSocketServiceImpl;
import com.ai.blog.strategy.context.UploadStrategyContext;
import com.ai.blog.vo.Result;
import com.ai.blog.vo.VoiceVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 博客信息控制器
 *
 * @author yezhiqiu
 * @date 2021/07/28
 */
@Tag(name = "博客信息模块")
@RestController
public class BlogInfoWebController {
    @Autowired
    private BlogInfoService blogInfoService;
    @Autowired
    private WebSocketServiceImpl webSocketService;
    @Autowired
    private UploadStrategyContext uploadStrategyContext;

    /**
     * 查看博客信息
     *
     * @return {@link Result<BlogHomeInfoDTO>} 博客信息
     */
    @Operation(summary = "查看博客信息")
    @GetMapping("/")
    public Result<BlogHomeInfoDTO> getBlogHomeInfo() {
        return Result.ok(blogInfoService.getBlogHomeInfo());
    }


    /**
     * 查看关于我信息
     *
     * @return {@link Result<String>} 关于我信息
     */
    @Operation(summary = "查看关于我信息")
    @GetMapping("/about")
    public Result<String> getAbout() {
        return Result.ok(blogInfoService.getAbout());
    }

    /**
     * 保存语音信息
     *
     * @param voiceVO 语音信息
     * @return {@link Result<String>} 语音地址
     */
    @Operation(summary = "上传语音")
    @PostMapping("/voice")
    public Result<String> sendVoice(VoiceVO voiceVO) {
        webSocketService.sendVoice(voiceVO);
        return Result.ok();
    }

    /**
     * 上传访客信息
     *
     * @return {@link Result}
     */
    @PostMapping("/report")
    public Result<?> report() {
        blogInfoService.report();
        return Result.ok();
    }

}

