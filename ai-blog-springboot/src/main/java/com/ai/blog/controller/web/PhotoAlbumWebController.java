package com.ai.blog.controller.web;

import com.ai.blog.dto.PhotoAlbumDTO;
import com.ai.blog.service.PhotoAlbumService;
import com.ai.blog.vo.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 菜队
 * @description: 相册控制器
 * @date 2025-03-06 21:06
 * @email gxy06x@qq.com
 */
@Tag(name = "相册模块")
@RestController
public class PhotoAlbumWebController {
    @Resource
    private PhotoAlbumService photoAlbumService;


    /**
     * 获取相册列表
     *
     * @return {@link Result < PhotoAlbumDTO >} 相册列表
     */
    @Operation(summary = "获取相册列表")
    @GetMapping("/photos/albums")
    public Result<List<PhotoAlbumDTO>> listPhotoAlbums() {
        return Result.ok(photoAlbumService.listPhotoAlbums());
    }
}
