package com.ai.blog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 参数配置表 config
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_config")
public class Config implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 参数主键 */

    @TableId(value = "id", type = IdType.AUTO)
    private Long configId;

    /** 参数名称 */

    private String configName;

    /** 参数键名 */

    private String configKey;

    /** 参数键值 */

    private String configValue;

    /** 系统内置（Y是 N否） */
    private String configType;
    /** 创建者 */
    private String createBy;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 更新者 */
    private String updateBy;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /** 备注 */
    private String remark;

}
