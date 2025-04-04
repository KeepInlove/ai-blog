package com.ai.blog.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 逻辑删除
 *
 * @author yezhiqiu
 * @date 2021/07/28
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "逻辑删除")
public class DeleteVO {

    /**
     * id列表
     */
    @NotNull(message = "id不能为空")
    @Schema(name = "idList", description = "id列表", required = true, example = "List<Integer>")
    private List<Integer> idList;

    /**
     * 状态值
     */
    @NotNull(message = "状态值不能为空")
    @Schema(name = "isDelete", description = "删除状态", required = true, example = "Integer")
    private Integer isDelete;

}
