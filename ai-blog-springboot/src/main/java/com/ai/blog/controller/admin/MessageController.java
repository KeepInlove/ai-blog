package com.ai.blog.controller.admin;

import com.ai.blog.annotation.OptLog;
import com.ai.blog.dto.MessageBackDTO;
import com.ai.blog.service.MessageService;
import com.ai.blog.vo.ConditionVO;
import com.ai.blog.vo.PageResult;
import com.ai.blog.vo.Result;
import com.ai.blog.vo.ReviewVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.ai.blog.constant.OptTypeConst.REMOVE;
import static com.ai.blog.constant.OptTypeConst.UPDATE;

/**
 * @author 菜队
 * @description: TODO
 * @date 2025-03-06 21:04
 * @email gxy06x@qq.com
 */
@Tag(name = "留言模块")
@RestController
public class MessageController {
    @Autowired
    private MessageService messageService;

    /**
     * 查看后台留言列表
     *
     * @param condition 条件
     * @return {@link Result < MessageBackDTO >} 留言列表
     */
    @Operation(summary = "查看后台留言列表")
    @GetMapping("/admin/messages")
    public Result<PageResult<MessageBackDTO>> listMessageBackDTO(ConditionVO condition) {
        return Result.ok(messageService.listMessageBackDTO(condition));
    }

    /**
     * 审核留言
     *
     * @param reviewVO 审核信息
     * @return {@link Result<>}
     */
    @OptLog(optType = UPDATE)
    @Operation(summary = "审核留言")
    @PutMapping("/admin/messages/review")
    public Result<?> updateMessagesReview(@Valid @RequestBody ReviewVO reviewVO) {
        messageService.updateMessagesReview(reviewVO);
        return Result.ok();
    }

    /**
     * 删除留言
     *
     * @param messageIdList 留言id列表
     * @return {@link Result<>}
     */
    @OptLog(optType = REMOVE)
    @Operation(summary = "删除留言")
    @DeleteMapping("/admin/messages")
    public Result<?> deleteMessages(@RequestBody List<Integer> messageIdList) {
        messageService.removeByIds(messageIdList);
        return Result.ok();
    }
}
