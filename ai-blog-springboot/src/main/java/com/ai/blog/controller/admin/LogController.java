package com.ai.blog.controller.admin;

import com.ai.blog.dto.OperationLogDTO;
import com.ai.blog.service.OperationLogService;
import com.ai.blog.vo.ConditionVO;
import com.ai.blog.vo.PageResult;
import com.ai.blog.vo.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 日志控制器
 *
 * @author yezhiqiu
 * @date 2021/07/27
 */
@Tag(name = "日志模块")
@RestController
public class LogController {
    @Autowired
    private OperationLogService operationLogService;

    /**
     * 查看操作日志
     *
     * @param conditionVO 条件
     * @return {@link Result<OperationLogDTO>} 日志列表
     */
    @Operation(summary = "查看操作日志")
    @GetMapping("/admin/operation/logs")
    public Result<PageResult<OperationLogDTO>> listOperationLogs(ConditionVO conditionVO) {
        return Result.ok(operationLogService.listOperationLogs(conditionVO));
    }

    /**
     * 删除操作日志
     *
     * @param logIdList 日志id列表
     * @return {@link Result<>}
     */
    @Operation(summary = "删除操作日志")
    @DeleteMapping("/admin/operation/logs")
    public Result<?> deleteOperationLogs(@RequestBody List<Integer> logIdList) {
        operationLogService.removeByIds(logIdList);
        return Result.ok();
    }

}
