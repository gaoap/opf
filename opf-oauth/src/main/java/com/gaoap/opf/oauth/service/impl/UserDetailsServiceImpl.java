package com.gaoap.opf.oauth.service.impl;


import com.gaoap.opf.common.core.http.HttpResult;
import com.gaoap.opf.common.core.vo.SysResource;
import com.gaoap.opf.common.core.vo.SysRole;
import com.gaoap.opf.common.core.vo.SysUser;
import com.gaoap.opf.oauth.entity.AuthUser;
import com.gaoap.opf.oauth.service.OpfAdminService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private OpfAdminService opfAdminService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        HttpResult<SysUser> userResult = opfAdminService.findByUsername(username);
        if (userResult.getCode() != HttpStatus.OK.value()) {
            throw new UsernameNotFoundException("用户:" + username + ",不存在!");
        }
        Set<SimpleGrantedAuthority> grantedAuthorities = new HashSet<>();
        SysUser userVo = new SysUser();
        BeanUtils.copyProperties(userResult.getData(), userVo);
        HttpResult<List<SysRole>> roleResult = opfAdminService.getRoleByUserId(userVo.getId());
        if (roleResult.getCode() == HttpStatus.OK.value()) {
            List<SysRole> roleVoList = roleResult.getData();
            for (SysRole role : roleVoList) {
                //角色必须是ROLE_开头，可以在数据库中设置
                SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_" + role.getCode());
                grantedAuthorities.add(grantedAuthority);
                //获取权限
                HttpResult<List<SysResource>> perResult = opfAdminService.getResource(role.getId());
                if (perResult.getCode() == HttpStatus.OK.value()) {
                    List<SysResource> resourceList = perResult.getData();
                    for (SysResource resource : resourceList
                    ) {
                        if (!StringUtils.isEmpty(resource.getUrl())) {
                            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(resource.getUrl());
                            grantedAuthorities.add(authority);
                        }
                    }
                }
            }
        }
        AuthUser user = new AuthUser(userVo.getName(), userVo.getPassword(), grantedAuthorities);
        user.setId(userVo.getId());
        return user;
    }
}
