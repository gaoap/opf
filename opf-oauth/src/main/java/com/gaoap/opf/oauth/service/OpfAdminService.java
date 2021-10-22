package com.gaoap.opf.oauth.service;


import com.gaoap.opf.common.core.http.HttpResult;
import com.gaoap.opf.common.core.vo.SysResource;
import com.gaoap.opf.common.core.vo.SysRole;
import com.gaoap.opf.common.core.vo.SysUser;
import com.gaoap.opf.oauth.service.impl.OpfAdminServiceFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "opf-admin", fallback = OpfAdminServiceFallbackFactory.class)
@Component("opfAdminService")
public interface OpfAdminService {
    @GetMapping("admin/sysUser/findByUsername/{username}")
    HttpResult<SysUser> findByUsername(@PathVariable("username") String username);

    @GetMapping("admin/sysRole/getRoleByUserId/{userId}")
    HttpResult<List<SysRole>> getRoleByUserId(@PathVariable("userId") Long userId);

    @GetMapping("admin/sysResource/getRoleResource/{roleId}")
    HttpResult<List<SysResource>> getResource(@PathVariable("roleId") Long roleId);
}
