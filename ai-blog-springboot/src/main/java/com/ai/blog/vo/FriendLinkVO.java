package com.ai.blog.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 友链VO
 *
 * @author yezhiqiu
 * @date 2021/07/28
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "友链")
public class FriendLinkVO {
    /**
     * id
     */
    @Schema(name = "categoryId", description = "友链id", example = "Integer")
    private Integer id;

    /**
     * 链接名
     */
    @NotBlank(message = "链接名不能为空")
    @Schema(name = "linkName", description = "友链名", example = "String", required = true)
    private String linkName;

    /**
     * 链接头像
     */
    @NotBlank(message = "链接头像不能为空")
    @Schema(name = "linkAvatar", description = "友链头像", example = "String", required = true)
    private String linkAvatar;

    /**
     * 链接地址
     */
    @NotBlank(message = "链接地址不能为空")
    @Schema(name = "linkAddress", description = "友链头像", example = "String", required = true)
    private String linkAddress;

    /**
     * 介绍
     */
    @NotBlank(message = "链接介绍不能为空")
    @Schema(name = "linkIntro", description = "友链头像", example = "String", required = true)
    private String linkIntro;

}
