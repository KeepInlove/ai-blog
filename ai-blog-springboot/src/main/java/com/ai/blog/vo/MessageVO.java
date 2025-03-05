package com.ai.blog.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 留言
 *
 * @author yezhiqiu
 * @date 2021/08/03
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "留言")
public class MessageVO {

    /**
     * 昵称
     */
    @NotBlank(message = "昵称不能为空")
    @Schema(name = "nickname", description = "昵称", required = true, example = "String")
    private String nickname;

    /**
     * 头像
     */
    @NotBlank(message = "头像不能为空")
    @Schema(name = "avatar", description = "头像", required = true, example = "String")
    private String avatar;

    /**
     * 留言内容
     */
    @NotBlank(message = "留言内容不能为空")
    @Schema(name = "messageContent", description = "留言内容", required = true, example = "String")
    private String messageContent;

    /**
     * 弹幕速度
     */
    @NotNull(message = "弹幕速度不能为空")
    @Schema(name = "time", description = "弹幕速度", required = true, example = "Integer")
    private Integer time;
}
