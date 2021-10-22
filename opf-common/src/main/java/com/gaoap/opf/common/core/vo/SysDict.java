package com.gaoap.opf.common.core.vo;


import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 字典表
 * </p>
 *
 * @author gaoyd
 * @since 2021-10-18
 */
@Data
public class SysDict implements Serializable {

    private static final long serialVersionUID = 1L;


    private Long id;

    /**
     * 数据值
     */
    private String value;

    /**
     * 标签名
     */
    private String label;

    /**
     * 类型
     */
    private String type;

    /**
     * 描述
     */
    private String description;

    /**
     * 排序（升序）
     */
    private BigDecimal sort;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新人
     */
    private String lastUpdateBy;

    /**
     * 更新时间
     */
    private Date lastUpdateTime;

    /**
     * 备注信息
     */
    private String remarks;

    /**
     * 是否删除  -1：已删除  0：正常
     */
    private Integer delFlag;


}
