package com.gaoap.opf.admin.controller;


import com.gaoap.opf.admin.entity.SysResource;
import com.gaoap.opf.admin.service.ISysResourceService;
import com.gaoap.opf.common.core.http.HttpResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 资源管理：URL、权限等 前端控制器
 * </p>
 *
 * @author gaoyd
 * @since 2021-10-18
 */
@Api(tags = "资源信息管理")
@RestController
@RequestMapping("/admin/sysResource")
public class SysResourceController {
    @Resource
    private ISysResourceService sysResourceService;

    @ApiOperation("根据角色ID获取资源，例如：URL、按钮、权限等")
    @GetMapping("/getRoleResource/{roleId}")
    HttpResult<List<SysResource>> getRoleResource(@PathVariable("roleId") Long roleId) {
        return HttpResult.ok(sysResourceService.getResourceByRoleId(roleId));
    }
}

