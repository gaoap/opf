package com.gaoap.opf.oauth.service.impl;


import com.gaoap.opf.common.core.http.HttpResult;
import com.gaoap.opf.common.core.vo.SysResource;
import com.gaoap.opf.common.core.vo.SysRole;
import com.gaoap.opf.common.core.vo.SysSubsystem;
import com.gaoap.opf.common.core.vo.SysUser;
import com.gaoap.opf.oauth.service.OpfAdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;


@Slf4j
@Component("opfAdminServiceFallbackFactory")
public class OpfAdminServiceFallbackFactory implements OpfAdminService {
    @Override
    public HttpResult<SysUser> findByUsername(String username) {
        return HttpResult.error(201, "调用失败");
    }

    @Override
    public HttpResult<List<SysRole>> getRoleByUserId(Long userId) {
        return HttpResult.error(201, "调用失败");
    }

    @Override
    public HttpResult<List<SysResource>> getResource(Long roleId) {
        return HttpResult.error(201, "调用失败");
    }

    @Override
    public HttpResult<List<SysSubsystem>> getSubsystem() {
        return HttpResult.error(201, "调用失败");
    }


}
