package com.ai.blog.handler;

import com.aliyun.oss.common.utils.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ai.blog.util.PageUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Optional;

import static com.ai.blog.constant.CommonConst.*;

/**
 * 分页拦截器
 *
 * @author yezhiqiu
 * @date 2021/07/18
 **/
public class PageableHandlerInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String currentPage = request.getParameter(CURRENT);
        String pageSize = Optional.ofNullable(request.getParameter(SIZE)).orElse(DEFAULT_SIZE);
        if (!StringUtils.isNullOrEmpty(currentPage)) {
            PageUtils.setCurrentPage(new Page<>(Long.parseLong(currentPage), Long.parseLong(pageSize)));
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        PageUtils.remove();
    }

}