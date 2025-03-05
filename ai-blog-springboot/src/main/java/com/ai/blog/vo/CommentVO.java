package com.ai.blog.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 评论
 *
 * @author yezhiqiu
 * @date 2021/08/10
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "评论")
public class CommentVO {

    /**
     * 回复用户id
     */
    @Schema(name = "replyUserId", description = "回复用户id", example = "Integer")
    private Integer replyUserId;

    /**
     * 评论主题id
     */
    @Schema(name = "topicId", description = "主题id", example = "Integer")
    private Integer topicId;

    /**
     * 评论内容
     */
    @NotBlank(message = "评论内容不能为空")
    @Schema(name = "commentContent", description = "评论内容", required = true, example = "String")
    private String commentContent;

    /**
     * 父评论id
     */
    @Schema(name = "parentId", description = "评论父id", example = "Integer")
    private Integer parentId;

    /**
     * 类型
     */
    @NotNull(message = "评论类型不能为空")
    @Schema(name = "type", description = "评论类型", example = "Integer")
    private Integer type;

}
