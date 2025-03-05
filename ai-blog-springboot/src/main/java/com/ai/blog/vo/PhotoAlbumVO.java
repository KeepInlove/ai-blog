package com.ai.blog.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 相册
 *
 * @author yezhiqiu
 * @date 2021/08/04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "相册")
public class PhotoAlbumVO {

    /**
     * 相册id
     */
    @Schema(name = "id", description = "相册id", required = true, example = "Integer")
    private Integer id;

    /**
     * 相册名
     */
    @NotBlank(message = "相册名不能为空")
    @Schema(name = "albumName", description = "相册名", required = true, example = "String")
    private String albumName;

    /**
     * 相册描述
     */
    @Schema(name = "albumDesc", description = "相册描述", example = "String")
    private String albumDesc;

    /**
     * 相册封面
     */
    @NotBlank(message = "相册封面不能为空")
    @Schema(name = "albumCover", description = "相册封面", required = true, example = "String")
    private String albumCover;

    /**
     * 状态值 1公开 2私密
     */
    @Schema(name = "status", description = "状态值", required = true, example = "Integer")
    private Integer status;

}
