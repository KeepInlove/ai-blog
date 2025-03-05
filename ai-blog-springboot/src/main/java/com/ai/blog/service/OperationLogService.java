package com.ai.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ai.blog.dto.OperationLogDTO;
import com.ai.blog.vo.PageResult;
import com.ai.blog.entity.OperationLog;
import com.ai.blog.vo.ConditionVO;

/**
 * 操作日志服务
 *
 * @author yezhiqiu
 * @date 2021/07/29
 */
public interface OperationLogService extends IService<OperationLog> {

    /**
     * 查询日志列表
     *
     * @param conditionVO 条件
     * @return 日志列表
     */
    PageResult<OperationLogDTO> listOperationLogs(ConditionVO conditionVO);

}
