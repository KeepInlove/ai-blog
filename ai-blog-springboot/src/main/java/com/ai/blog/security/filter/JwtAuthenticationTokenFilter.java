package com.ai.blog.security.filter;

import com.ai.blog.dto.UserInfoDTO;
import com.ai.blog.handler.FilterInvocationSecurityMetadataSourceImpl;
import com.ai.blog.service.impl.TokenService;
import com.ai.blog.util.SecurityUtils;
import com.ai.blog.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

/**
 
 * @description: token过滤器 验证token有效性
 * @date 2025-03-02 13:42
 * @email gxy06x@qq.com
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter
{
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private FilterInvocationSecurityMetadataSourceImpl filterInvocationSecurityMetadataSource;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException
    {
        String requestURI = request.getRequestURI();
        UserInfoDTO loginUser = tokenService.getLoginUser(request);
        if (StringUtils.isNotNull(loginUser) && StringUtils.isNull(SecurityUtils.getAuthentication()))
        {
            tokenService.verifyToken(loginUser);
            // 关键点：通过UserDetailsService加载完整权限信息
            UserDetails userDetails = userDetailsService.loadUserByUsername(loginUser.getUsername());
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null,userDetails.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }else if (loginUser == null && SecurityUtils.getAuthentication() == null) {
            // 这里查询是否是匿名接口
            if (filterInvocationSecurityMetadataSource.isAnonymousUrl(requestURI)) {
                UsernamePasswordAuthenticationToken anonymousToken =
                        new UsernamePasswordAuthenticationToken("anonymousUser", null, List.of(new SimpleGrantedAuthority("ROLE_ANONYMOUS")));
                SecurityContextHolder.getContext().setAuthentication(anonymousToken);
            } else {
                // 非匿名接口直接让Spring处理，403
                SecurityContextHolder.clearContext();
            }
        }

        chain.doFilter(request, response);
    }
}
