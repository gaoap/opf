package com.gaoap.opf.admin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gaoap.opf.admin.entity.SysUser;
import com.gaoap.opf.admin.service.ISysRoleService;
import com.gaoap.opf.admin.service.ISysUserService;
import com.gaoap.opf.common.core.http.HttpResult;
import com.gaoap.opf.common.core.vo.SysRole;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 角色管理 前端控制器
 * </p>
 *
 * @author gaoyd
 * @since 2021-10-18
 */
@Api(tags = "角色信息管理")
@RestController
@RequestMapping("/admin/sysRole")
public class SysRoleController {
    /**
     * 服务对象
     */
    @Resource
    private ISysRoleService sysRoleService;


    @ApiOperation("根据用户ID获取角色")
    @GetMapping("/getRoleByUserId/{userId}")
    public HttpResult getRoleByUserId(@PathVariable("userId") Long userId) {
        System.out.println("userId:"+userId);
        return HttpResult.ok(sysRoleService.getRoleByUserId(userId));
    }
}

