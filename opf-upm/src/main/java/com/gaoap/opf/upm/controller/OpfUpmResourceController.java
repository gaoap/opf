package com.gaoap.opf.upm.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gaoap.opf.upm.common.http.CommonPage;
import com.gaoap.opf.upm.common.http.CommonResult;
import com.gaoap.opf.upm.entity.OpfUpmResource;
import com.gaoap.opf.upm.security.filter.handler.DynamicSecurityMetadataSource;
import com.gaoap.opf.upm.service.IOpfUpmResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 后台资源表 前端控制器
 * </p>
 *
 * @author gaoyd
 * @since 2021-11-01
 */
@RestController
@RequestMapping("/upm/opfUpmResource")
@Api(tags = "后台资源管理")
@Slf4j
public class OpfUpmResourceController {
    @Autowired
    private IOpfUpmResourceService resourceService;

    @ApiOperation("添加后台资源")
    @PostMapping(value = "/add")
    public CommonResult add(@RequestBody OpfUpmResource umsResource) {
        boolean success = resourceService.add(umsResource);
        if (success) {
            return CommonResult.success(null);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("修改后台资源")
    @PostMapping(value = "/update/{id}")
    public CommonResult update(@PathVariable Long id,
                               @RequestBody OpfUpmResource umsResource) {
        boolean success = resourceService.update(id, umsResource);
        if (success) {
            return CommonResult.success(null);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("根据ID获取资源详情")
    @GetMapping(value = "/{id}")
    public CommonResult<OpfUpmResource> getItem(@PathVariable Long id) {
        OpfUpmResource umsResource = resourceService.getById(id);
        return CommonResult.success(umsResource);
    }

    @ApiOperation("根据ID删除后台资源")
    @PostMapping(value = "/delete/{id}")
    public CommonResult delete(@PathVariable Long id) {
        boolean success = resourceService.delete(id);
        if (success) {
            return CommonResult.success(null);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("分页模糊查询后台资源")
    @GetMapping(value = "/list")
    public CommonResult<CommonPage<OpfUpmResource>> list(@RequestParam(required = false) Long categoryId,
                                                         @RequestParam(required = false) String nameKeyword,
                                                         @RequestParam(required = false) String urlKeyword,
                                                         @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                                         @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        Page<OpfUpmResource> resourceList = resourceService.list(categoryId, nameKeyword, urlKeyword, pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(resourceList));
    }

    @ApiOperation("查询所有后台资源")
    @GetMapping(value = "/listAll")
    public CommonResult<List<OpfUpmResource>> listAll() {
        List<OpfUpmResource> resourceList = resourceService.list();
        return CommonResult.success(resourceList);
    }
}

