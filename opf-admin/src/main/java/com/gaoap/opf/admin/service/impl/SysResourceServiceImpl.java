package com.gaoap.opf.admin.service.impl;

import com.gaoap.opf.admin.entity.SysResource;
import com.gaoap.opf.admin.entity.SysRole;
import com.gaoap.opf.admin.mapper.SysResourceMapper;
import com.gaoap.opf.admin.mapper.SysRoleMapper;
import com.gaoap.opf.admin.service.ISysResourceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 资源管理：URL、权限等 服务实现类
 * </p>
 *
 * @author gaoyd
 * @since 2021-10-18
 */
@Service
public class SysResourceServiceImpl extends ServiceImpl<SysResourceMapper, SysResource> implements ISysResourceService {
    @Autowired
    private SysResourceMapper sysResourceMapper;

    public List<SysResource> getResourceByRoleId(Long roleId) {
        return sysResourceMapper.getResourceByRoleId(roleId);
    }
}
