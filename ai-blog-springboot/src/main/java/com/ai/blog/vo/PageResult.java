package com.ai.blog.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 分页对象
 *
 * @author yezhiqiu
 * @date 2021/08/10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "分页对象")
public class PageResult<T> {

    /**
     * 分页列表
     */
    @Schema(name = "recordList", description = "分页列表", required = true, example = "List<T>")
    private List<T> recordList;

    /**
     * 总数
     */
    @Schema(name = "count", description = "总数", required = true, example = "Integer")
    private Long count;

}
