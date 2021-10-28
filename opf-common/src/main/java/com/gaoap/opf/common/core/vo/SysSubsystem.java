package com.gaoap.opf.common.core.vo;

import lombok.Data;

import java.util.Date;

/**
 * <p>
 * 微服务中，系统编号管理。方便授权
 * </p>
 *
 * @author gaoyd
 * @since 2021-10-27
 */
@Data
public class SysSubsystem {


    /**
     * 系统编号
     */
    private String subId;

    /**
     * 系统名称
     */
    private String subName;

    /**
     * 系统备注
     */
    private String remark;

    /**
     * 最后更新时间
     */
    private Date lastUpdateTime;

    /**
     * 最后更新人
     */
    private String lastUpdateBy;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 是否删除 -1：已删除 0：正常
     */
    private Integer delFlag;


}
