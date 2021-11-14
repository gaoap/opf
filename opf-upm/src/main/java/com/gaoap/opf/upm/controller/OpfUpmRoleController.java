package com.gaoap.opf.upm.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gaoap.opf.upm.common.http.CommonPage;
import com.gaoap.opf.upm.common.http.CommonResult;
import com.gaoap.opf.upm.entity.OpfUpmMenu;
import com.gaoap.opf.upm.entity.OpfUpmResource;
import com.gaoap.opf.upm.entity.OpfUpmRole;
import com.gaoap.opf.upm.service.IOpfUpmRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 后台用户角色表 前端控制器
 * </p>
 *
 * @author gaoyd
 * @since 2021-11-01
 */
@RestController
@Api(tags = "后台用户角色管理")
@RequestMapping("/upm/opfUpmRole")
@Slf4j
public class OpfUpmRoleController {
    @Autowired
    private IOpfUpmRoleService roleService;

    @ApiOperation("添加角色")
    @PostMapping(value = "/add")
    public CommonResult add(@RequestBody OpfUpmRole role) {
        boolean success = roleService.add(role);
        if (success) {
            return CommonResult.success(null);
        }
        return CommonResult.failed();
    }

    @ApiOperation("修改角色")
    @PostMapping(value = "/update/{id}")
    public CommonResult update(@PathVariable Long id, @RequestBody OpfUpmRole role) {
        role.setId(id);
        boolean success = roleService.updateById(role);
        if (success) {
            return CommonResult.success(null);
        }
        return CommonResult.failed();
    }

    @ApiOperation("批量删除角色")
    @PostMapping(value = "/delete")
    public CommonResult delete(@RequestParam("ids") List<Long> ids) {
        boolean success = roleService.delete(ids);
        if (success) {
            return CommonResult.success(null);
        }
        return CommonResult.failed();
    }


    @ApiOperation("获取所有角色")
    @GetMapping(value = "/listAll")
    public CommonResult<List<OpfUpmRole>> listAll() {
        List<OpfUpmRole> roleList = roleService.list();
        return CommonResult.success(roleList);
    }

    @ApiOperation("根据角色名称分页获取角色列表")
    @GetMapping(value = "/list")
    public CommonResult<CommonPage<OpfUpmRole>> list(@RequestParam(value = "keyword", required = false) String keyword,
                                                     @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                                     @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        Page<OpfUpmRole> roleList = roleService.list(keyword, pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(roleList));
    }

    @ApiOperation("修改角色状态")
    @PostMapping(value = "/updateStatus/{id}")
    public CommonResult updateStatus(@PathVariable Long id, @RequestParam(value = "status") Integer status) {
        OpfUpmRole umsRole = new OpfUpmRole();
        umsRole.setId(id);
        umsRole.setStatus(status);
        boolean success = roleService.updateById(umsRole);
        if (success) {
            return CommonResult.success(null);
        }
        return CommonResult.failed();
    }

    @ApiOperation("获取角色相关菜单")
    @GetMapping(value = "/listMenu/{roleId}")
    public CommonResult<List<OpfUpmMenu>> listMenu(@PathVariable Long roleId) {
        List<OpfUpmMenu> roleList = roleService.listMenu(roleId);
        return CommonResult.success(roleList);
    }

    @ApiOperation("获取角色相关资源")
    @GetMapping(value = "/listResource/{roleId}")
    public CommonResult<List<OpfUpmResource>> listResource(@PathVariable Long roleId) {
        List<OpfUpmResource> roleList = roleService.listResource(roleId);
        return CommonResult.success(roleList);
    }

    @ApiOperation("给角色分配菜单")
    @PostMapping(value = "/allocMenus")
    public CommonResult allocMenu(@RequestParam Long roleId, @RequestParam List<Long> menuIds) {
        int count = roleService.allocMenus(roleId, menuIds);
        return CommonResult.success(count);
    }

    @ApiOperation("给角色分配资源")
    @PostMapping(value = "/allocResources")
    public CommonResult allocResource(@RequestParam Long roleId, @RequestParam List<Long> resourceIds) {
        int count = roleService.allocResources(roleId, resourceIds);
        return CommonResult.success(count);
    }
}

