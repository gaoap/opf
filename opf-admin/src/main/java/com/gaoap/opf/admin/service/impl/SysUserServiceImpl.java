package com.gaoap.opf.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gaoap.opf.admin.entity.SysResource;
import com.gaoap.opf.admin.entity.SysRole;
import com.gaoap.opf.admin.entity.SysUser;
import com.gaoap.opf.admin.mapper.SysUserMapper;
import com.gaoap.opf.admin.security.common.AdminUserDetails;
import com.gaoap.opf.admin.security.common.RoleType;
import com.gaoap.opf.admin.service.ISysResourceService;
import com.gaoap.opf.admin.service.ISysRoleResourceService;
import com.gaoap.opf.admin.service.ISysRoleService;
import com.gaoap.opf.admin.service.ISysUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.*;

/**
 * <p>
 * 用户管理 服务实现类
 * </p>
 *
 * @author gaoyd
 * @since 2021-10-18
 */
@Service
@Slf4j
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {
    @Autowired
    private ISysRoleService sysRoleService;
    @Autowired
    private ISysRoleResourceService sysRoleResourceService;
    @Autowired
    private ISysResourceService sysResourceService;

    @Override
    public AdminUserDetails authorizationInformationByName(String username) {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("name", username); //name为 "Jone" 的用户
        SysUser user = getOne(wrapper);

        if (user == null) {
            throw new UsernameNotFoundException("用户:" + username + ",不存在!");
        }
        Set<RoleType> roleTypes = new HashSet<>();
        log.info(user.getName() + "-------:------" + user.getPassword());
        List<SysRole> roleVoList = this.sysRoleService.getRoleByUserId(user.getId());
        for (SysRole role : roleVoList) {
            //角色必须是ROLE_开头，可以在数据库中设置
            if (!StringUtils.isEmpty(role.getCode())) {
                RoleType roleType = new RoleType(role.getCode());
                roleTypes.add(roleType);
            }

            //获取权限
            List<SysResource> resourceList = sysResourceService.getResourceByRoleId(role.getId());

            for (SysResource resource : resourceList) {
                if (!StringUtils.isEmpty(resource.getUrl()) && !StringUtils.isEmpty(resource.getPerms())) {
                    RoleType roleType1 = new RoleType(resource.getPerms(), resource.getId(), 2);
                    roleTypes.add(roleType1);
                }
            }

        }

        AdminUserDetails adminUserDetails = new AdminUserDetails(user, roleTypes);
        log.info(adminUserDetails.getPassword() + "-------adminUserDetails:------" + adminUserDetails.getPassword());
        return adminUserDetails;
    }
}
