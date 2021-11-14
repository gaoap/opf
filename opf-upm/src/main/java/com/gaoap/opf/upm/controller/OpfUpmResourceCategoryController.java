package com.gaoap.opf.upm.controller;


import com.gaoap.opf.upm.common.http.CommonResult;
import com.gaoap.opf.upm.entity.OpfUpmResourceCategory;
import com.gaoap.opf.upm.service.IOpfUpmResourceCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 资源分类表 前端控制器
 * </p>
 *
 * @author gaoyd
 * @since 2021-11-01
 */
@RestController
@RequestMapping("/upm/opfUpmResourceCategory")
@Api(tags = "后台资源分类管理")
@Slf4j
public class OpfUpmResourceCategoryController {
    @Autowired
    private IOpfUpmResourceCategoryService resourceCategoryService;

    @ApiOperation("查询所有后台资源分类")
    @GetMapping(value = "/listAll")
    public CommonResult<List<OpfUpmResourceCategory>> listAll() {
        List<OpfUpmResourceCategory> resourceList = resourceCategoryService.listAll();
        return CommonResult.success(resourceList);
    }

    @ApiOperation("添加后台资源分类")
    @PostMapping(value = "/add")
    public CommonResult add(@RequestBody OpfUpmResourceCategory umsResourceCategory) {
        boolean success = resourceCategoryService.add(umsResourceCategory);
        if (success) {
            return CommonResult.success(null);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("修改后台资源分类")
    @PostMapping(value = "/update/{id}")
    public CommonResult update(@PathVariable Long id,
                               @RequestBody OpfUpmResourceCategory umsResourceCategory) {
        umsResourceCategory.setId(id);
        boolean success = resourceCategoryService.updateById(umsResourceCategory);
        if (success) {
            return CommonResult.success(null);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("根据ID删除后台资源")
    @PostMapping(value = "/delete/{id}")
    public CommonResult delete(@PathVariable Long id) {
        boolean success = resourceCategoryService.removeById(id);
        if (success) {
            return CommonResult.success(null);
        } else {
            return CommonResult.failed();
        }
    }
}

