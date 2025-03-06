package com.ai.blog.controller.admin;

import com.ai.blog.annotation.OptLog;
import com.ai.blog.dto.FriendLinkBackDTO;
import com.ai.blog.service.FriendLinkService;
import com.ai.blog.vo.ConditionVO;
import com.ai.blog.vo.FriendLinkVO;
import com.ai.blog.vo.PageResult;
import com.ai.blog.vo.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.ai.blog.constant.OptTypeConst.REMOVE;
import static com.ai.blog.constant.OptTypeConst.SAVE_OR_UPDATE;

/**
 * @author 菜队
 * @description: TODO
 * @date 2025-03-06 21:02
 * @email gxy06x@qq.com
 */
@Tag(name = "友链模块")
@RestController
public class FriendLinkController {
    @Autowired
    private FriendLinkService friendLinkService;
    /**
     * 查看后台友链列表
     *
     * @param condition 条件
     * @return {@link Result < FriendLinkBackDTO >} 后台友链列表
     */
    @Operation(summary = "查看后台友链列表")
    @GetMapping("/admin/links")
    public Result<PageResult<FriendLinkBackDTO>> listFriendLinkDTO(ConditionVO condition) {
        return Result.ok(friendLinkService.listFriendLinkDTO(condition));
    }

    /**
     * 保存或修改友链
     *
     * @param friendLinkVO 友链信息
     * @return {@link Result<>}
     */
    @OptLog(optType = SAVE_OR_UPDATE)
    @Operation(summary = "保存或修改友链")
    @PostMapping("/admin/links")
    public Result<?> saveOrUpdateFriendLink(@Valid @RequestBody FriendLinkVO friendLinkVO) {
        friendLinkService.saveOrUpdateFriendLink(friendLinkVO);
        return Result.ok();
    }

    /**
     * 删除友链
     *
     * @param linkIdList 友链id列表
     * @return {@link Result<>}
     */
    @OptLog(optType = REMOVE)
    @Operation(summary = "删除友链")
    @DeleteMapping("/admin/links")
    public Result<?> deleteFriendLink(@RequestBody List<Integer> linkIdList) {
        friendLinkService.removeByIds(linkIdList);
        return Result.ok();
    }
}
