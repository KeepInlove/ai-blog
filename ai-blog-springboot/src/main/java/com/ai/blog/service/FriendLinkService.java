package com.ai.blog.service;

import com.ai.blog.dto.FriendLinkBackDTO;
import com.ai.blog.dto.FriendLinkDTO;
import com.ai.blog.vo.ConditionVO;
import com.ai.blog.vo.PageResult;
import com.ai.blog.entity.FriendLink;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ai.blog.vo.FriendLinkVO;

import java.util.List;

/**
 * 友链服务
 *
 * @author yezhiqiu
 * @date 2021/07/29
 */
public interface FriendLinkService extends IService<FriendLink> {

    /**
     * 查看友链列表
     *
     * @return 友链列表
     */
    List<FriendLinkDTO> listFriendLinks();

    /**
     * 查看后台友链列表
     *
     * @param condition 条件
     * @return 友链列表
     */
    PageResult<FriendLinkBackDTO> listFriendLinkDTO(ConditionVO condition);

    /**
     * 保存或更新友链
     *
     * @param friendLinkVO 友链
     */
    void saveOrUpdateFriendLink(FriendLinkVO friendLinkVO);

}
