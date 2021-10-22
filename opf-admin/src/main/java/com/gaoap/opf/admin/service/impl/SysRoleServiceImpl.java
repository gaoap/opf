package com.gaoap.opf.admin.service.impl;

import com.gaoap.opf.admin.entity.SysRole;
import com.gaoap.opf.admin.mapper.SysRoleMapper;
import com.gaoap.opf.admin.service.ISysRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 角色管理 服务实现类
 * </p>
 *
 * @author gaoyd
 * @since 2021-10-18
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {
    @Autowired
    private SysRoleMapper sysRoleMapper;

    public List<SysRole> getRoleByUserId(Long userId) {
        return sysRoleMapper.getRoleByUserId(userId);
    }
}
