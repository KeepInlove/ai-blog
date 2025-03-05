package com.ai.blog.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 分类VO
 *
 * @author yezhiqiu
 * @date 2021/07/28
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "分类")
public class CategoryVO {

    /**
     * id
     */
    @Schema(name = "id", description = "分类id", example = "Integer")
    private Integer id;

    /**
     * 分类名
     */
    @NotBlank(message = "分类名不能为空")
    @Schema(name = "categoryName", description = "分类名", required = true, example = "String")
    private String categoryName;

}
