package com.gaoap.opf.common.core.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 角色管理
 * </p>
 *
 * @author gaoyd
 * @since 2021-10-18
 */
@Data
public class SysRole implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Long id;

    /**
     * 角色名称
     */
    private String name;
    /**
     * 角色代码
     */
    private String code;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 更新人
     */
    private String lastUpdateBy;

    /**
     * 是否删除  -1：已删除  0：正常
     */
    private Integer delFlag;


}
