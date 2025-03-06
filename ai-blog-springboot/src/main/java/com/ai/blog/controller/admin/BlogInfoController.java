package com.ai.blog.controller.admin;

import com.ai.blog.annotation.OptLog;
import com.ai.blog.dto.BlogBackInfoDTO;
import com.ai.blog.enums.FilePathEnum;
import com.ai.blog.service.BlogInfoService;
import com.ai.blog.service.impl.WebSocketServiceImpl;
import com.ai.blog.strategy.context.UploadStrategyContext;
import com.ai.blog.vo.BlogInfoVO;
import com.ai.blog.vo.Result;
import com.ai.blog.vo.WebsiteConfigVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.ai.blog.constant.OptTypeConst.UPDATE;

/**
 * @author 菜队
 * @description: 博客信息控制器
 * @date 2025-03-06 20:58
 * @email gxy06x@qq.com
 */
@Tag(name = "博客信息模块")
@RestController
public class BlogInfoController {

    @Resource
    private BlogInfoService blogInfoService;
    @Resource
    private WebSocketServiceImpl webSocketService;
    @Resource
    private UploadStrategyContext uploadStrategyContext;


    /**
     * 查看后台信息
     *
     * @return {@link Result < BlogBackInfoDTO >} 后台信息
     */
    @Operation(summary = "查看后台信息")
    @GetMapping("/admin")
    public Result<BlogBackInfoDTO> getBlogBackInfo() {
        return Result.ok(blogInfoService.getBlogBackInfo());
    }

    /**
     * 上传博客配置图片
     *
     * @param file 文件
     * @return {@link Result<String>} 博客配置图片
     */
    @Operation(summary = "上传博客配置图片")
    @Parameter(name = "file",  description= "图片", required = true, example = "MultipartFile")
    @PostMapping("/admin/config/images")
    public Result<String> savePhotoAlbumCover(MultipartFile file) {
        return Result.ok(uploadStrategyContext.executeUploadStrategy(file, FilePathEnum.CONFIG.getPath()));
    }

    /**
     * 更新网站配置
     *
     * @param websiteConfigVO 网站配置信息
     * @return {@link Result}
     */
    @Operation(summary = "更新网站配置")
    @PutMapping("/admin/website/config")
    public Result<?> updateWebsiteConfig(@Valid @RequestBody WebsiteConfigVO websiteConfigVO) {
        blogInfoService.updateWebsiteConfig(websiteConfigVO);
        return Result.ok();
    }

    /**
     * 获取网站配置
     *
     * @return {@link Result<WebsiteConfigVO>} 网站配置
     */
    @Operation(summary = "获取网站配置")
    @GetMapping("/admin/website/config")
    public Result<WebsiteConfigVO> getWebsiteConfig() {
        return Result.ok(blogInfoService.getWebsiteConfig());
    }
    /**
     * 修改关于我信息
     *
     * @param blogInfoVO 博客信息
     * @return {@link Result<>}
     */
    @OptLog(optType = UPDATE)
    @Operation(summary = "修改关于我信息")
    @PutMapping("/admin/about")
    public Result<?> updateAbout(@Valid @RequestBody BlogInfoVO blogInfoVO) {
        blogInfoService.updateAbout(blogInfoVO);
        return Result.ok();
    }

}
