package com.gaoap.opf.upm.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gaoap.opf.upm.common.http.CommonPage;
import com.gaoap.opf.upm.common.http.CommonResult;
import com.gaoap.opf.upm.dto.OpfUpmMenuNode;
import com.gaoap.opf.upm.entity.OpfUpmMenu;
import com.gaoap.opf.upm.service.IOpfUpmMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 后台菜单表 前端控制器
 * </p>
 *
 * @author gaoyd
 * @since 2021-11-01
 */
@RestController
@Api(tags = "后台菜单管理")
@RequestMapping("/upm/opfUpmMenu")
public class OpfUpmMenuController {
    @Autowired
    private IOpfUpmMenuService menuService;

    @ApiOperation("添加后台菜单")
    @PostMapping(value = "/add")
    public CommonResult add(@RequestBody OpfUpmMenu umsMenu) {
        boolean success = menuService.add(umsMenu);
        if (success) {
            return CommonResult.success(null);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("修改后台菜单")
    @PostMapping(value = "/update/{id}")
    public CommonResult update(@PathVariable Long id,
                               @RequestBody OpfUpmMenu umsMenu) {
        boolean success = menuService.update(id, umsMenu);
        if (success) {
            return CommonResult.success(null);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("根据ID获取菜单详情")
    @GetMapping(value = "/{id}")
    public CommonResult<OpfUpmMenu> getItem(@PathVariable Long id) {
        OpfUpmMenu umsMenu = menuService.getById(id);
        return CommonResult.success(umsMenu);
    }

    @ApiOperation("根据ID删除后台菜单")
    @PostMapping(value = "/delete/{id}")
    public CommonResult delete(@PathVariable Long id) {
        boolean success = menuService.removeById(id);
        if (success) {
            return CommonResult.success(null);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("分页查询后台菜单")
    @GetMapping(value = "/list/{parentId}")
    public CommonResult<CommonPage<OpfUpmMenu>> list(@PathVariable Long parentId,
                                                     @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                                     @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        Page<OpfUpmMenu> menuList = menuService.list(parentId, pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(menuList));
    }

    @ApiOperation("树形结构返回所有菜单列表")
    @GetMapping(value = "/treeList")
    public CommonResult<List<OpfUpmMenuNode>> treeList() {
        List<OpfUpmMenuNode> list = menuService.treeList();
        return CommonResult.success(list);
    }

    @ApiOperation("修改菜单显示状态")
    @PostMapping(value = "/updateHidden/{id}")
    public CommonResult updateHidden(@PathVariable Long id, @RequestParam("hidden") Integer hidden) {
        boolean success = menuService.updateHidden(id, hidden);
        if (success) {
            return CommonResult.success(null);
        } else {
            return CommonResult.failed();
        }
    }
}

