package com.ai.blog.controller.web;


import com.ai.blog.annotation.AccessLimit;
import com.ai.blog.dto.MessageDTO;
import com.ai.blog.service.MessageService;
import com.ai.blog.vo.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 留言控制器
 *
 * @author xiaojie
 * @date 2021/07/29
 */
@Tag(name = "留言模块")
@RestController
public class MessageWebController {
    @Autowired
    private MessageService messageService;

    /**
     * 添加留言
     *
     * @param messageVO 留言信息
     * @return {@link Result <>}
     */
    @AccessLimit(seconds = 60, maxCount = 1)
    @Operation(summary = "添加留言")
    @PostMapping("/messages")
    public Result<?> saveMessage(@Valid @RequestBody MessageVO messageVO) {
        messageService.saveMessage(messageVO);
        return Result.ok();
    }

    /**
     * 查看留言列表
     *
     * @return {@link Result<MessageDTO>} 留言列表
     */
    @Operation(summary = "查看留言列表")
    @GetMapping("/messages")
    public Result<List<MessageDTO>> listMessages() {
        return Result.ok(messageService.listMessages());
    }



}

