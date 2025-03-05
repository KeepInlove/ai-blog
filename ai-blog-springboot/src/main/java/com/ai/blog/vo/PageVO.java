package com.ai.blog.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 页面信息
 *
 * @author yezhiqiu
 * @date 2021/08/07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "页面")
public class PageVO {

    /**
     * 页面id
     */
    @Schema(name = "id", description = "页面id", required = true, example = "123")
    private Integer id;

    /**
     * 页面名
     */
    @NotBlank(message = "页面名称不能为空")
    @Schema(name = "pageName", description = "页面名称", required = true, example = "String")
    private String pageName;

    /**
     * 页面标签
     */
    @NotBlank(message = "页面标签不能为空")
    @Schema(name = "pageLabel", description = "页面标签", required = true, example = "String")
    private String pageLabel;

    /**
     * 页面封面
     */
    @NotBlank(message = "页面封面不能为空")
    @Schema(name = "pageCover", description = "页面封面", required = true, example = "String")
    private String pageCover;

}
