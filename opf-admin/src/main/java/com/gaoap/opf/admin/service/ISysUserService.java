package com.gaoap.opf.admin.service;

import com.gaoap.opf.admin.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gaoap.opf.admin.security.common.AdminUserDetails;

/**
 * <p>
 * 用户管理 服务类
 * </p>
 *
 * @author gaoyd
 * @since 2021-10-18
 */
public interface ISysUserService extends IService<SysUser> {
    public AdminUserDetails authorizationInformationByName(String username);
}
