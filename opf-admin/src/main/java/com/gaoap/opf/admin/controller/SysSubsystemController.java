package com.gaoap.opf.admin.controller;


import com.gaoap.opf.admin.entity.SysResource;
import com.gaoap.opf.admin.entity.SysSubsystem;
import com.gaoap.opf.admin.service.ISysResourceService;
import com.gaoap.opf.admin.service.ISysSubsystemService;
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
 * 微服务中，系统编号管理。方便授权 前端控制器
 * </p>
 *
 * @author gaoyd
 * @since 2021-10-27
 */
@Api(tags = "子系统信息")
@RestController
@RequestMapping("/admin/sysSubsystem")
public class SysSubsystemController {
    @Resource
    private ISysSubsystemService sysSubsystemService;

    @ApiOperation("获取全部系统")
    @GetMapping("/subsystem")
    HttpResult<List<SysSubsystem>> getSysSubsystem() {
        return HttpResult.ok(sysSubsystemService.list());
    }
}

