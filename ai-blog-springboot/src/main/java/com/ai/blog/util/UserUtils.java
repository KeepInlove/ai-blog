package com.ai.blog.util;

import com.ai.blog.dto.UserInfoDTO;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;


/**
 * 用户工具类
 *
 * @author yezhiqiu
 * @date 2021/08/10
 */
@Component
public class UserUtils {

    /**
     * 获取当前登录用户
     *
     * @return 用户登录信息
     */
    public static UserInfoDTO getLoginUser() {
//        return (UserInfoDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserInfoDTO dto = new UserInfoDTO();
        dto.setId(1L);
        dto.setUserInfoId(1);
        dto.setIpAddress("127.0.0.1");
        return dto;
    }

}
