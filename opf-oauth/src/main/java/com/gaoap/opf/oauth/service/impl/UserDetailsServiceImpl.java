package com.gaoap.opf.oauth.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.gaoap.opf.common.core.http.HttpResult;
import com.gaoap.opf.common.core.vo.SysResource;
import com.gaoap.opf.common.core.vo.SysRole;
import com.gaoap.opf.common.core.vo.SysSubsystem;
import com.gaoap.opf.common.core.vo.SysUser;
import com.gaoap.opf.oauth.entity.AuthUser;
import com.gaoap.opf.oauth.service.OpfAdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;

import java.util.*;


@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private OpfAdminService opfAdminService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("----------------------username:------------------------------" + username);
        HttpResult<SysUser> userResult = opfAdminService.findByUsername(username);
        log.info("----------------------userResult:------------------------------" + userResult.getData());
        if (userResult.getCode() != HttpStatus.OK.value()) {
            throw new UsernameNotFoundException("用户:" + username + ",不存在!");
        }
        Set<SimpleGrantedAuthority> grantedAuthorities = new HashSet<>();
        SysUser userVo = new SysUser();
        BeanUtils.copyProperties(userResult.getData(), userVo);
        HttpResult<List<SysRole>> roleResult = opfAdminService.getRoleByUserId(userVo.getId());
        log.info("----------------------roleResult:------------------------------" + roleResult.getData().size());
        HttpResult<List<SysSubsystem>> sysSubsystems = opfAdminService.getSubsystem();
        log.info("----------------------sysSubsystems:------------------------------" + JSONObject.toJSONString(sysSubsystems));
        Map<String, String> subsystems = new HashMap<>();
        for (SysSubsystem s : sysSubsystems.getData()) {
            subsystems.put(s.getSubId(), s.getSubName());
        }
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
                        String subname = subsystems.get(resource.getSubId());
                        if (!StringUtils.isEmpty(resource.getUrl())) {
                            SimpleGrantedAuthority authority = new SimpleGrantedAuthority("/" + subname + resource.getUrl());
                            grantedAuthorities.add(authority);
                        }
                    }
                }
            }
        }
        AuthUser user = new AuthUser(userVo.getName(), userVo.getPassword(), grantedAuthorities);
        log.info("name:{},password:{}", user.getUsername(), user.getPassword());
        user.setId(userVo.getId());
        return user;
    }
}
