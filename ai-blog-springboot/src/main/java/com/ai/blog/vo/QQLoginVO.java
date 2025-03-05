package com.ai.blog.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * QQ登录
 *
 * @author yezhqiu
 * @date 2021/06/14
 * @since 1.0.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "qq登录信息")
public class QQLoginVO {

    /**
     * openId
     */
    @NotBlank(message = "openId不能为空")
    @Schema(name = "openId", description = "qq openId", required = true, example = "String")
    private String openId;

    /**
     * accessToken
     */
    @NotBlank(message = "accessToken不能为空")
    @Schema(name = "accessToken", description = "qq accessToken", required = true, example = "String")
    private String accessToken;

}
