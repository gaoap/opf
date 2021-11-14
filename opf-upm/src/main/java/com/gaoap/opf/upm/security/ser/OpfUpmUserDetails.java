package com.gaoap.opf.upm.security.ser;


import com.gaoap.opf.upm.entity.OpfUpmResource;
import com.gaoap.opf.upm.entity.OpfUpmUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * SpringSecurity需要的用户详情
 */
public class OpfUpmUserDetails implements UserDetails {
    private OpfUpmUser opfUpmUser;
    private List<OpfUpmResource> resourceList;

    public OpfUpmUserDetails(OpfUpmUser opfUpmUser, List<OpfUpmResource> resourceList) {
        this.opfUpmUser = opfUpmUser;
        this.resourceList = resourceList;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //返回当前用户的角色
        return resourceList.stream()
                .map(resource -> new SimpleGrantedAuthority(resource.getId() + ":" + resource.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return opfUpmUser.getPassword();
    }

    @Override
    public String getUsername() {
        return opfUpmUser.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return opfUpmUser.getStatus().equals(1);
    }
}
