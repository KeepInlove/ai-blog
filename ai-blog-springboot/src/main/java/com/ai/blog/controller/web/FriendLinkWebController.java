package com.ai.blog.controller.web;


import com.ai.blog.dto.FriendLinkDTO;
import com.ai.blog.service.FriendLinkService;
import com.ai.blog.vo.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 友链控制器
 *
 * @author yezhiqiu
 * @date 2021/07/29
 */
@Tag(name = "友链模块")
@RestController
public class FriendLinkWebController {
    @Autowired
    private FriendLinkService friendLinkService;

    /**
     * 查看友链列表
     *
     * @return {@link Result <FriendLinkDTO>} 友链列表
     */
    @Operation(summary = "查看友链列表")
    @GetMapping("/links")
    public Result<List<FriendLinkDTO>> listFriendLinks() {
        return Result.ok(friendLinkService.listFriendLinks());
    }



}

