package com.gaoap.opf.common.core.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 资源管理：URL、权限等
 * </p>
 *
 * @author gaoyd
 * @since 2021-10-18
 */
@Data
public class SysResource implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Long id;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 父菜单ID，一级菜单为0
     */
    private Long parentId;

    /**
     * 菜单URL,类型：1.普通页面（如用户管理， /sys/user） 2.嵌套完整外部页面，以http(s)开头的链接 3.嵌套服务器页面，使用iframe:前缀+目标URL(如SQL监控， iframe:/druid/login.html, iframe:前缀会替换成服务器地址)
     */
    private String url;

    /**
     * 授权(多个用逗号分隔，如：sys:user:add,sys:user:edit)
     */
    private String perms;

    /**
     * 类型   0：目录   1：菜单   2：按钮
     */
    private Integer type;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 排序
     */
    private Integer orderNum;

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

    /**
     * 状态  0：未启用   1：正常
     */
    private Integer status;
    /**
     * 系统编号
     */
    private String subId;



}
