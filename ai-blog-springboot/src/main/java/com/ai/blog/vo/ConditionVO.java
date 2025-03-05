package com.ai.blog.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


/**
 * 查询条件
 *
 * @author yezhiqiu
 * @date 2021/07/29
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "查询条件")
public class ConditionVO {

    /**
     * 页码
     */
    @Schema(name = "current", description = "页码", example = "Long")
    private Long current;

    /**
     * 条数
     */
    @Schema(name = "size", description = "条数", example = "Long")
    private Long size;

    /**
     * 搜索内容
     */
    @Schema(name = "keywords", description = "搜索内容", example = "String")
    private String keywords;

    /**
     * 分类id
     */
    @Schema(name = "categoryId", description = "分类id", example = "Integer")
    private Integer categoryId;

    /**
     * 标签id
     */
    @Schema(name = "tagId", description = "标签id", example = "Integer")
    private Integer tagId;

    /**
     * 相册id
     */
    @Schema(name = "albumId", description = "相册id", example = "Integer")
    private Integer albumId;

    /**
     * 登录类型
     */
    @Schema(name = "type", description = "登录类型", example = "Integer")
    private Integer loginType;

    /**
     * 类型
     */
    @Schema(name = "type", description = "类型", example = "Integer")
    private Integer type;

    /**
     * 状态
     */
    @Schema(name = "status", description = "状态", example = "Integer")
    private Integer status;

    /**
     * 开始时间
     */
    @Schema(name = "startTime", description = "开始时间", example = "LocalDateTime")
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    @Schema(name = "endTime", description = "结束时间", example = "LocalDateTime")
    private LocalDateTime endTime;

    /**
     * 是否删除
     */
    @Schema(name = "isDelete", description = "是否删除", example = "Integer")
    private Integer isDelete;

    /**
     * 是否审核
     */
    @Schema(name = "isReview", description = "是否审核", example = "Integer")
    private Integer isReview;

}
