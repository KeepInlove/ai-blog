package com.ai.blog.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 菜单
 *
 * @author yezhiqiu
 * @date 2021/08/03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "菜单")
public class MenuVO {

    /**
     * id
     */
    @Schema(name = "id", description = "菜单id", example = "Integer")
    private Integer id;

    /**
     * 菜单名
     */
    @NotBlank(message = "菜单名不能为空")
    @Schema(name = "name", description = "菜单名", example = "String")
    private String name;

    /**
     * icon
     */
    @NotBlank(message = "菜单icon不能为空")
    @Schema(name = "icon", description = "菜单icon", example = "String")
    private String icon;

    /**
     * 路径
     */
    @NotBlank(message = "路径不能为空")
    @Schema(name = "path", description = "路径", example = "String")
    private String path;

    /**
     * 组件
     */
    @NotBlank(message = "组件不能为空")
    @Schema(name = "component", description = "组件", example = "String")
    private String component;

    /**
     * 排序
     */
    @NotNull(message = "排序不能为空")
    @Schema(name = "orderNum", description = "排序", example = "Integer")
    private Integer orderNum;

    /**
     * 父id
     */
    @Schema(name = "parentId", description = "父id", example = "Integer")
    private Integer parentId;

    /**
     * 是否隐藏
     */
    @Schema(name = "isHidden", description = "是否隐藏", example = "Integer")
    private Integer isHidden;

}
