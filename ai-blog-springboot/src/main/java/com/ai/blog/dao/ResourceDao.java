package com.ai.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ai.blog.entity.Resource;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 资源
 *
 * @author yezhiqiu
 * @date 2021/08/10
 */
@Repository
public interface ResourceDao extends BaseMapper<Resource> {

    List<String> listAnonymousUrls();

}
