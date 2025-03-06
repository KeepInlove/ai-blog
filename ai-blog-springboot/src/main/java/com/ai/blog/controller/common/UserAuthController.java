package com.ai.blog.controller.common;


import com.ai.blog.annotation.AccessLimit;
import com.ai.blog.dto.UserInfoDTO;
import com.ai.blog.service.UserAuthService;
import com.ai.blog.vo.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户账号控制器
 *
 * @author yezhiqiu
 * @date 2021/07/28
 */
@Tag(name = "用户账号模块")
@RestController
public class UserAuthController {
    @Autowired
    private UserAuthService userAuthService;

    /**
     * 发送邮箱验证码
     *
     * @param username 用户名
     * @return {@link Result <>}
     */
    @AccessLimit(seconds = 60, maxCount = 1)
    @Operation(summary = "发送邮箱验证码")
    @Parameter(name = "username", description = "用户名", required = true, example = "String")
    @GetMapping("/users/code")
    public Result<?> sendCode(String username) {
        userAuthService.sendCode(username);
        return Result.ok();
    }


    /**
     * 用户注册
     *
     * @param user 用户信息
     * @return {@link Result<>}
     */
    @Operation(summary = "用户注册")
    @PostMapping("/register")
    public Result<?> register(@Valid @RequestBody UserVO user) {
        userAuthService.register(user);
        return Result.ok();
    }

    /**
     * 修改密码
     *
     * @param user 用户信息
     * @return {@link Result<>}
     */
    @Operation(summary = "修改密码")
    @PutMapping("/users/password")
    public Result<?> updatePassword(@Valid @RequestBody UserVO user) {
        userAuthService.updatePassword(user);
        return Result.ok();
    }

    /**
     * 微博登录
     *
     * @param weiBoLoginVO 微博登录信息
     * @return {@link Result<UserInfoDTO>} 用户信息
     */
    @Operation(summary = "微博登录")
    @PostMapping("/users/oauth/weibo")
    public Result<UserInfoDTO> weiboLogin(@Valid @RequestBody WeiboLoginVO weiBoLoginVO) {
        return Result.ok(userAuthService.weiboLogin(weiBoLoginVO));
    }

    /**
     * qq登录
     *
     * @param qqLoginVO qq登录信息
     * @return {@link Result<UserInfoDTO>} 用户信息
     */
    @Operation(summary = "qq登录")
    @PostMapping("/users/oauth/qq")
    public Result<UserInfoDTO> qqLogin(@Valid @RequestBody QQLoginVO qqLoginVO) {
        return Result.ok(userAuthService.qqLogin(qqLoginVO));
    }
    /***
     * @description: 登录
     * @param: loginRequest
     */
    @Operation(summary = "普通登录")
    @PostMapping("/login")
    public Result<?> login(@RequestBody UserVO loginUserVO) {
        return Result.ok(userAuthService.login(loginUserVO));
    }


}

