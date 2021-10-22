package com.gaoap.opf.admin.service;

import com.gaoap.opf.admin.entity.SysResource;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 资源管理：URL、权限等 服务类
 * </p>
 *
 * @author gaoyd
 * @since 2021-10-18
 */
public interface ISysResourceService extends IService<SysResource> {
    public List<SysResource> getResourceByRoleId(Long roleId);
}
