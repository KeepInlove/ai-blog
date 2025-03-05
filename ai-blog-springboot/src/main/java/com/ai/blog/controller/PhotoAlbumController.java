package com.ai.blog.controller;

import com.ai.blog.annotation.OptLog;
import com.ai.blog.dto.PhotoAlbumBackDTO;
import com.ai.blog.dto.PhotoAlbumDTO;
import com.ai.blog.enums.FilePathEnum;
import com.ai.blog.service.PhotoAlbumService;
import com.ai.blog.strategy.context.UploadStrategyContext;
import com.ai.blog.vo.ConditionVO;
import com.ai.blog.vo.PageResult;
import com.ai.blog.vo.PhotoAlbumVO;
import com.ai.blog.vo.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.ai.blog.constant.OptTypeConst.REMOVE;
import static com.ai.blog.constant.OptTypeConst.SAVE_OR_UPDATE;

/**
 * 相册控制器
 *
 * @author yezhiqiu
 * @date 2021/08/04
 */
@Tag(name = "相册模块")
@RestController
public class PhotoAlbumController {
    @Autowired
    private UploadStrategyContext uploadStrategyContext;
    @Autowired
    private PhotoAlbumService photoAlbumService;

    /**
     * 上传相册封面
     *
     * @param file 文件
     * @return {@link Result<String>} 相册封面地址
     */
    @Operation(summary = "上传相册封面")
    @Parameter(name = "file", description = "相册封面", required = true, example = "MultipartFile")
    @PostMapping("/admin/photos/albums/cover")
    public Result<String> savePhotoAlbumCover(MultipartFile file) {
        return Result.ok(uploadStrategyContext.executeUploadStrategy(file, FilePathEnum.PHOTO.getPath()));
    }

    /**
     * 保存或更新相册
     *
     * @param photoAlbumVO 相册信息
     * @return {@link Result<>}
     */
    @OptLog(optType = SAVE_OR_UPDATE)
    @Operation(summary = "保存或更新相册")
    @PostMapping("/admin/photos/albums")
    public Result<?> saveOrUpdatePhotoAlbum(@Valid @RequestBody PhotoAlbumVO photoAlbumVO) {
        photoAlbumService.saveOrUpdatePhotoAlbum(photoAlbumVO);
        return Result.ok();
    }

    /**
     * 查看后台相册列表
     *
     * @param condition 条件
     * @return {@link Result<PhotoAlbumBackDTO>} 相册列表
     */
    @Operation(summary = "查看后台相册列表")
    @GetMapping("/admin/photos/albums")
    public Result<PageResult<PhotoAlbumBackDTO>> listPhotoAlbumBacks(ConditionVO condition) {
        return Result.ok(photoAlbumService.listPhotoAlbumBacks(condition));
    }

    /**
     * 获取后台相册列表信息
     *
     * @return {@link Result<PhotoAlbumDTO>} 相册列表信息
     */
    @Operation(summary = "获取后台相册列表信息")
    @GetMapping("/admin/photos/albums/info")
    public Result<List<PhotoAlbumDTO>> listPhotoAlbumBackInfos() {
        return Result.ok(photoAlbumService.listPhotoAlbumBackInfos());
    }

    /**
     * 根据id获取后台相册信息
     *
     * @param albumId 相册id
     * @return {@link Result}相册信息
     */
    @Operation(summary = "根据id获取后台相册信息")
    @Parameter(name = "albumId", description = "相册id", required = true, example = "Integer")
    @GetMapping("/admin/photos/albums/{albumId}/info")
    public Result<PhotoAlbumBackDTO> getPhotoAlbumBackById(@PathVariable("albumId") Integer albumId) {
        return Result.ok(photoAlbumService.getPhotoAlbumBackById(albumId));
    }

    /**
     * 根据id删除相册
     *
     * @param albumId 相册id
     * @return {@link Result}
     */
    @OptLog(optType = REMOVE)
    @Operation(summary = "根据id删除相册")
    @Parameter(name = "albumId", description = "相册id", required = true, example = "Integer")
    @DeleteMapping("/admin/photos/albums/{albumId}")
    public Result<?> deletePhotoAlbumById(@PathVariable("albumId") Integer albumId) {
        photoAlbumService.deletePhotoAlbumById(albumId);
        return Result.ok();
    }

    /**
     * 获取相册列表
     *
     * @return {@link Result<PhotoAlbumDTO>} 相册列表
     */
    @Operation(summary = "获取相册列表")
    @GetMapping("/photos/albums")
    public Result<List<PhotoAlbumDTO>> listPhotoAlbums() {
        return Result.ok(photoAlbumService.listPhotoAlbums());
    }

}
