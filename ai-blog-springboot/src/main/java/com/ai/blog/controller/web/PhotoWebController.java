package com.ai.blog.controller.web;

import com.ai.blog.dto.PhotoDTO;
import com.ai.blog.service.PhotoService;
import com.ai.blog.vo.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * 照片控制器
 *
 * @author yezhiqiu
 * @date 2021/08/05
 */
@Tag(name = "照片模块")
@RestController
public class PhotoWebController {
    @Resource
    private PhotoService photoService;
    /**
     * 根据相册id查看照片列表
     *
     * @param albumId 相册id
     * @return {@link Result<PhotoDTO>} 照片列表
     */
    @Operation(summary = "根据相册id查看照片列表")
    @GetMapping("/albums/{albumId}/photos")
    public Result<PhotoDTO> listPhotosByAlbumId(@PathVariable("albumId") Integer albumId) {
        return Result.ok(photoService.listPhotosByAlbumId(albumId));
    }

}
