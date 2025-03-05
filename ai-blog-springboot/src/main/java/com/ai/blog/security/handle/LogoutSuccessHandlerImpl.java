package com.ai.blog.security.handle;

import com.ai.blog.dto.UserInfoDTO;
import com.ai.blog.service.impl.TokenService;
import com.ai.blog.util.StringUtils;
import com.ai.blog.vo.Result;
import com.alibaba.fastjson.JSON;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import java.io.IOException;

import static com.ai.blog.constant.CommonConst.APPLICATION_JSON;

/**
 
 * @description: TODO
 * @date 2025-03-02 14:50
 * @email gxy06x@qq.com
 */
@Configuration
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {
    @Autowired
    private TokenService tokenService;

    /**
     * 退出处理
     *
     * @return
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        UserInfoDTO loginUser = tokenService.getLoginUser(request);
        if (StringUtils.isNotNull(loginUser)) {
            String userName = loginUser.getUsername();
            // 删除用户缓存记录
            tokenService.delLoginUser(loginUser.getToken());
            // 记录用户退出日志
            //AsyncManager.me().execute(AsyncFactory.recordLogininfor(userName, Constants.LOGOUT, "退出成功"));
        }
        response.setContentType(APPLICATION_JSON);
        response.getWriter().write(JSON.toJSONString(Result.ok()));
    }
}
