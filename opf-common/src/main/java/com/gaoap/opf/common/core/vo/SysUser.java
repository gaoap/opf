package com.gaoap.opf.common.core.vo;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 用户管理
 * </p>
 *
 * @author gaoyd
 * @since 2021-10-18
 */
@Data
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Long id;

    /**
     * 用户名
     */
    private String name;

    /**
     * 密码
     */
    private String password;

    /**
     * 盐
     */
    private String salt;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 状态  0：禁用   1：正常
     */
    private Integer status;

    /**
     * 机构ID
     */
    private Long deptId;

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
