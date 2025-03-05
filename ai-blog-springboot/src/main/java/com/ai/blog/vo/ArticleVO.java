package com.ai.blog.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


/**
 * 文章
 *
 * @author yezhiqiu
 * @date 2021/08/03
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "文章")
public class ArticleVO {

    /**
     * 文章id
     */
    @Schema(name = "id", description = "文章id", example = "Integer")
    private Integer id;

    /**
     * 标题
     */
    @NotBlank(message = "文章标题不能为空")
    @Schema(name = "articleTitle", description = "文章标题", required = true, example = "String")
    private String articleTitle;

    /**
     * 内容
     */
    @NotBlank(message = "文章内容不能为空")
    @Schema(name = "articleContent", description = "文章内容", required = true, example = "String")
    private String articleContent;

    /**
     * 文章封面
     */
    @Schema(name = "articleCover", description = "文章缩略图", example = "String")
    private String articleCover;

    /**
     * 文章分类
     */
    @Schema(name = "category", description = "文章分类", example = "Integer")
    private String categoryName;

    /**
     * 文章标签
     */
    @Schema(name = "tagNameList", description = "文章标签", example = "List<Integer>")
    private List<String> tagNameList;

    /**
     * 文章类型
     */
    @Schema(name = "type", description = "文章类型", example = "Integer")
    private Integer type;

    /**
     * 原文链接
     */
    @Schema(name = "originalUrl", description = "原文链接", example = "String")
    private String originalUrl;

    /**
     * 是否置顶
     */
    @Schema(name = "isTop", description = "是否置顶", example = "Integer")
    private Integer isTop;

    /**
     * 文章状态 1.公开 2.私密 3.评论可见
     */
    @Schema(name = "status", description = "文章状态", example = "String")
    private Integer status;

}
