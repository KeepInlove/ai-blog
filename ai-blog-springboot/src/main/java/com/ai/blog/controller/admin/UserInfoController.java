package com.ai.blog.controller.admin;

import com.ai.blog.annotation.OptLog;
import com.ai.blog.dto.UserAreaDTO;
import com.ai.blog.dto.UserBackDTO;
import com.ai.blog.dto.UserOnlineDTO;
import com.ai.blog.service.UserAuthService;
import com.ai.blog.service.UserInfoService;
import com.ai.blog.vo.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.ai.blog.constant.OptTypeConst.UPDATE;

/**
 * @author Guoxinyu
 * @description: 用户信息
 * @date 2025-03-06 21:59
 * @email gxy06x@qq.com
 */
@Tag(name = "用管理模块")
@RestController
public class UserInfoController {
    @Resource
    private UserInfoService userInfoService;

    @Resource
    private UserAuthService userAuthService;
    /**
     * 修改用户角色
     *
     * @param userRoleVO 用户角色信息
     * @return {@link Result <>}
     */
    @OptLog(optType = UPDATE)
    @Operation(summary = "修改用户角色")
    @PutMapping("/admin/users/role")
    public Result<?> updateUserRole(@Valid @RequestBody UserRoleVO userRoleVO) {
        userInfoService.updateUserRole(userRoleVO);
        return Result.ok();
    }

    /**
     * 修改用户禁用状态
     *
     * @param userDisableVO 用户禁用信息
     * @return {@link Result<>}
     */
    @OptLog(optType = UPDATE)
    @Operation(summary = "修改用户禁用状态")
    @PutMapping("/admin/users/disable")
    public Result<?> updateUserDisable(@Valid @RequestBody UserDisableVO userDisableVO) {
        userInfoService.updateUserDisable(userDisableVO);
        return Result.ok();
    }

    /**
     * 查看在线用户
     *
     * @param conditionVO 条件
     * @return {@link Result< UserOnlineDTO >} 在线用户列表
     */
    @Operation(summary = "查看在线用户")
    @GetMapping("/admin/users/online")
    public Result<PageResult<UserOnlineDTO>> listOnlineUsers(ConditionVO conditionVO) {
        return Result.ok(userInfoService.listOnlineUsers(conditionVO));
    }

    /**
     * 下线用户
     *
     * @param userInfoId 用户信息
     * @return {@link Result<>}
     */
    @Operation(summary = "下线用户")
    @DeleteMapping("/admin/users/{userInfoId}/online")
    public Result<?> removeOnlineUser(@PathVariable("userInfoId") Integer userInfoId) {
        userInfoService.removeOnlineUser(userInfoId);
        return Result.ok();
    }
    /**
     * 修改管理员密码
     *
     * @param passwordVO 密码信息
     * @return {@link Result<>}
     */
    @Operation(summary = "修改管理员密码")
    @PutMapping("/admin/users/password")
    public Result<?> updateAdminPassword(@Valid @RequestBody PasswordVO passwordVO) {
        userAuthService.updateAdminPassword(passwordVO);
        return Result.ok();
    }

    /**
     * 获取用户区域分布
     *
     * @param conditionVO 条件
     * @return {@link Result< UserAreaDTO >} 用户区域分布
     */
    @Operation(summary = "获取用户区域分布")
    @GetMapping("/admin/users/area")
    public Result<List<UserAreaDTO>> listUserAreas(ConditionVO conditionVO) {
        return Result.ok(userAuthService.listUserAreas(conditionVO));
    }

    /**
     * 查询后台用户列表
     *
     * @param condition 条件
     * @return {@link Result< UserBackDTO >} 用户列表
     */
    @Operation(summary = "查询后台用户列表")
    @GetMapping("/admin/users")
    public Result<PageResult<UserBackDTO>> listUsers(ConditionVO condition) {
        return Result.ok(userAuthService.listUserBackDTO(condition));
    }


}
