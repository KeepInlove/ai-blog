package com.ai.blog.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户信息vo
 *
 * @author yezhiqiu
 * @date 2021/08/03
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "用户信息对象")
public class UserInfoVO {

    /**
     * 用户昵称
     */
    @NotBlank(message = "昵称不能为空")
    @Schema(name = "nickname", description = "昵称", example = "String")
    private String nickname;

    /**
     * 用户简介
     */
    @Schema(name = "intro", description = "介绍", example = "String")
    private String intro;

    /**
     * 个人网站
     */
    @Schema(name = "webSite", description = "个人网站", example = "String")
    private String webSite;
}
