package com.ai.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 后台相册
 *
 * @author yezhiqiu
 * @date 2021/08/04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PhotoAlbumBackDTO {

    /**
     * 相册id
     */
    private Integer id;

    /**
     * 相册名
     */
    private String albumName;

    /**
     * 相册描述
     */
    private String albumDesc;

    /**
     * 相册封面
     */
    private String albumCover;

    /**
     * 照片数量
     */
    private Long photoCount;

    /**
     * 状态值 1公开 2私密
     */
    private Integer status;

}
