package com.ai.blog.handler;

import com.ai.blog.dao.ResourceDao;
import com.ai.blog.dao.RoleDao;
import com.ai.blog.dto.ResourceRoleDTO;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;

/**
 * 接口拦截规则
 *
 * @author yezhiqiu
 * @date 2021/07/27
 */
@Component
@Slf4j
public class FilterInvocationSecurityMetadataSourceImpl implements FilterInvocationSecurityMetadataSource {

    /**
     * 资源角色列表
     */
    private static List<ResourceRoleDTO> resourceRoleList;
    private static List<String> anonymousUrls;
    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Autowired
    private RoleDao roleDao;
    @Autowired
    private ResourceDao resourceDao;

    /**
     * 加载资源角色信息
     */
    @PostConstruct
    private void loadDataSource() {
        resourceRoleList = roleDao.listResourceRoles();
        anonymousUrls = resourceDao.listAnonymousUrls()
                .stream()
                .map(String::toLowerCase)
                .toList();
        log.info("加载资源-角色映射：{} 条", resourceRoleList.size());
        log.info("加载匿名资源列表：{} 条", anonymousUrls.size());
    }

        /**
         * 清空接口角色信息
         */
    public void clearDataSource() {
        resourceRoleList = null;
        anonymousUrls = null;
        log.info("资源角色列表和匿名URL列表已清空");
    }
    public boolean isAnonymousUrl(String requestUrl) {
        return anonymousUrls != null && anonymousUrls.stream().anyMatch(url -> antPathMatcher.match(url, requestUrl.toLowerCase()));
    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        // 修改接口角色关系后重新加载
        if (CollectionUtils.isEmpty(resourceRoleList)) {
            this.loadDataSource();
        }
        FilterInvocation fi = (FilterInvocation) object;
        // 获取用户请求方式
        String method = fi.getRequest().getMethod();
        // 获取用户请求Url
        String url = fi.getRequest().getRequestURI();
        // 匿名资源直接返回ROLE_ANONYMOUS
        if (isAnonymousUrl(url)) {
            return SecurityConfig.createList("ROLE_ANONYMOUS");
        }
        // 获取接口角色信息，若为匿名接口则放行，若无对应角色则禁止
         for (ResourceRoleDTO resourceRoleDTO : resourceRoleList) {
            if (antPathMatcher.match(resourceRoleDTO.getUrl(), url)
                    && resourceRoleDTO.getRequestMethod().equalsIgnoreCase(method)) {
                if (CollectionUtils.isEmpty(resourceRoleDTO.getRoleList())) {
                    return SecurityConfig.createList("disable");
                }
                return SecurityConfig.createList(resourceRoleDTO.getRoleList().toArray(new String[0]));
            }
        }
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return FilterInvocation.class.isAssignableFrom(aClass);
    }

}
