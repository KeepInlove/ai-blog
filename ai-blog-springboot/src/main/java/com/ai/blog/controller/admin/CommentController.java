package com.ai.blog.controller.admin;

import com.ai.blog.annotation.OptLog;
import com.ai.blog.dto.CommentBackDTO;
import com.ai.blog.service.CommentService;
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
 * @description: 评论控制器
 * @date 2025-03-06 21:01
 * @email gxy06x@qq.com
 */
@Tag(name = "评论模块")
@RestController
public class CommentController {
    @Autowired
    private CommentService commentService;

    /**
     * 审核评论
     *
     * @param reviewVO 审核信息
     * @return {@link Result <>}
     */
    @OptLog(optType = UPDATE)
    @Operation(summary = "审核评论")
    @PutMapping("/admin/comments/review")
    public Result<?> updateCommentsReview(@Valid @RequestBody ReviewVO reviewVO) {
        commentService.updateCommentsReview(reviewVO);
        return Result.ok();
    }

    /**
     * 删除评论
     *
     * @param commentIdList 评论id列表
     * @return {@link Result<>}
     */
    @OptLog(optType = REMOVE)
    @Operation(summary = "删除评论")
    @DeleteMapping("/admin/comments")
    public Result<?> deleteComments(@RequestBody List<Integer> commentIdList) {
        commentService.removeByIds(commentIdList);
        return Result.ok();
    }

    /**
     * 查询后台评论
     *
     * @param condition 条件
     * @return {@link Result< CommentBackDTO >} 后台评论
     */
    @Operation(summary = "查询后台评论")
    @GetMapping("/admin/comments")
    public Result<PageResult<CommentBackDTO>> listCommentBackDTO(ConditionVO condition) {
        return Result.ok(commentService.listCommentBackDTO(condition));
    }
}
