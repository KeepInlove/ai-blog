package com.ai.blog.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

/**
 * 声音签证官
 *
 * @author yezhiqiu
 * @date 2021/07/28
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "音频")
public class VoiceVO {

    /**
     * 消息类型
     */
    @Schema(name = "type", description = "消息类型", required = true, example = "Integer")
    private Integer type;

    /**
     * 文件
     */
    @Schema(name = "file", description = "文件", required = true, example = "MultipartFile")
    private MultipartFile file;

    /**
     * 用户id
     */
    @Schema(name = "userId", description = "用户id", required = true, example = "Integer")
    private Integer userId;

    /**
     * 用户昵称
     */
    @Schema(name = "nickname", description = "用户昵称", required = true, example = "String")
    private String nickname;

    /**
     * 用户头像
     */
    @Schema(name = "avatar", description = "用户头像", required = true, example = "String")
    private String avatar;

    /**
     * 聊天内容
     */
    @Schema(name = "content", description = "聊天内容", required = true, example = "String")
    private String content;

    /**
     * 创建时间
     */
    @Schema(name = "createTime", description = "创建时间", required = true, example = "Date")
    private Date createTime;

    /**
     * 用户登录ip
     */
    @Schema(name = "ipAddress", description = "用户登录ip", required = true, example = "String")
    private String ipAddress;

    /**
     * ip来源
     */
    @Schema(name = "ipSource", description = "ip来源", required = true, example = "String")
    private String ipSource;

}
