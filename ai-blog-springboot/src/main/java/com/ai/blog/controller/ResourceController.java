package com.ai.blog.controller;

import com.ai.blog.dto.LabelOptionDTO;
import com.ai.blog.dto.ResourceDTO;
import com.ai.blog.service.ResourceService;
import com.ai.blog.vo.ConditionVO;
import com.ai.blog.vo.ResourceVO;
import com.ai.blog.vo.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 资源控制器
 *
 * @author yezhiqiu
 * @date 2021/07/27
 */
@Tag(name = "资源模块")
@RestController
public class ResourceController {
    @Autowired
    private ResourceService resourceService;

    /**
     * 查看资源列表
     *
     * @return {@link Result<ResourceDTO>} 资源列表
     */
    @Operation(summary = "查看资源列表")
    @GetMapping("/admin/resources")
    public Result<List<ResourceDTO>> listResources(ConditionVO conditionVO) {
        return Result.ok(resourceService.listResources(conditionVO));
    }

    /**
     * 删除资源
     *
     * @param resourceId 资源id
     * @return {@link Result<>}
     */
    @Operation(summary = "删除资源")
    @DeleteMapping("/admin/resources/{resourceId}")
    public Result<?> deleteResource(@PathVariable("resourceId") Integer resourceId) {
        resourceService.deleteResource(resourceId);
        return Result.ok();
    }

    /**
     * 新增或修改资源
     *
     * @param resourceVO 资源信息
     * @return {@link Result<>}
     */
    @Operation(summary = "新增或修改资源")
    @PostMapping("/admin/resources")
    public Result<?> saveOrUpdateResource(@RequestBody @Valid ResourceVO resourceVO) {
        resourceService.saveOrUpdateResource(resourceVO);
        return Result.ok();
    }

    /**
     * 查看角色资源选项
     *
     * @return {@link Result<LabelOptionDTO>} 角色资源选项
     */
    @Operation(summary = "查看角色资源选项")
    @GetMapping("/admin/role/resources")
    public Result<List<LabelOptionDTO>> listResourceOption() {
        return Result.ok(resourceService.listResourceOption());
    }


}
