package com.gaoap.opf.upm.controller;


import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gaoap.opf.upm.common.http.CommonPage;
import com.gaoap.opf.upm.common.http.CommonResult;
import com.gaoap.opf.upm.common.http.ResultCode;
import com.gaoap.opf.upm.dto.OpfUpmUserLoginParam;
import com.gaoap.opf.upm.dto.OpfUpmUserParam;
import com.gaoap.opf.upm.dto.UpdateOpfUpmUserPasswordParam;
import com.gaoap.opf.upm.entity.OpfUpmRole;
import com.gaoap.opf.upm.entity.OpfUpmUser;
import com.gaoap.opf.upm.service.IOpfUpmRoleService;
import com.gaoap.opf.upm.service.IOpfUpmUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 后台用户表 前端控制器
 * </p>
 *
 * @author gaoyd
 * @since 2021-11-01
 */
//使用@RestController注解，可以省去每个方法体单独使用@ResponseBody。
@RestController
//Swagger定义的接口名称
@Api(tags = "后台用户管理")
//访问此http服务的基础路径
@RequestMapping("/upm/opfUpmUser")
@Slf4j
public class OpfUpmUserController {
    @Value("${opf.upm.jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${opf.upm.jwt.tokenHead}")
    private String tokenHead;
    @Autowired
    private IOpfUpmUserService userService;
    @Autowired
    private IOpfUpmRoleService roleService;

    @ApiOperation(value = "用户注册" )
    @PostMapping(value = "/register")
    public CommonResult<OpfUpmUser> register(@Validated @RequestBody OpfUpmUserParam umsUserParam) {
        OpfUpmUser umsUser = userService.register(umsUserParam);
        if (umsUser == null) {
            return CommonResult.failed();
        }
        return CommonResult.success(umsUser);
    }

    //Swagger定义的接口名称
    @ApiOperation(value = "登录以后返回token")
    //访问此接口服务的完整路径为：http://ip:9021/upm/opfUpmUser/login需要使用post协议
    @PostMapping(value = "/login")
    //@Validated代表参数校验 @RequestBody 代表参数传递使用JSON格式  ，参数约定见类：OpfUpmUserParam
    //参数如果违反约定，会由src/main/java/com/gaoap/opf/upm/common/exception/
    // GlobalExceptionHandler.java 统一处理（这个类涉及Springboot的机制，回头分析用法）
    public CommonResult login(@Validated @RequestBody OpfUpmUserLoginParam umsUserLoginParam) {
        //log使用过注解@Slf4j定义的。主要是利用Lombok插件，简化代码开发
        log.info("准备登录。。。。。。");
        //请求登录实际处理类。如果返回token，则表明登录成功。没有返回token则登录失败
        String token = userService.login(umsUserLoginParam.getUsername(), umsUserLoginParam.getPassword());
        if (token == null) {
            return CommonResult.validateFailed("用户名或密码错误");
        }
        log.info("准备登录。。。。。。{}", token);
        Map<String, String> tokenMap = new HashMap<>();
        //具体的token内容
        tokenMap.put("token", token);
        // token的前缀
        tokenMap.put("tokenHead", tokenHead);
        //headers中储存token的key
        tokenMap.put("tokenHeader", tokenHeader);
        return CommonResult.success(tokenMap);
        /**
         * 登录成功返回的格式为：
         * {
         *     "code": 200,
         *     "message": "操作成功",
         *     "data": {
         *         "tokenHead": "Bearer",
         *         "tokenHeader": "Authorization",
         *         "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImNyZWF0ZWQiOjE2MzU3NzQyNjA1OTgsImV4cCI6MTY0Mjk3NDI2MH0.VNJkeMuA7TGyyrRovYRr2KzFkhbtfzPCMTV4X_vdaulqtDb1egBdNdEf3BV9LX4QFKniwWB9c4KvcSPgCzse5w"
         *     }
         * }
         */
    }


    @ApiOperation(value = "刷新token")
    @GetMapping(value = "/refreshToken")
    public CommonResult refreshToken(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        String refreshToken = userService.refreshToken(token);
        if (refreshToken == null) {
            return CommonResult.failed("token已经过期！");
        }
        Map<String, String> tokenMap = new HashMap<>();
        //具体的token内容
        tokenMap.put("token", token);
        // token的前缀
        tokenMap.put("tokenHead", tokenHead);
        //headers中储存token的key
        tokenMap.put("tokenHeader", tokenHeader);
        return CommonResult.success(tokenMap);
    }

    @ApiOperation(value = "获取当前登录用户信息")
    @GetMapping(value = "/info")
    public CommonResult getUserInfo(Principal principal) {
        if (principal == null) {
            return CommonResult.unauthorized(null);
        }
        String username = principal.getName();
        OpfUpmUser umsUser = userService.getUserByUsername(username);
        Map<String, Object> data = new HashMap<>();
        data.put("username", umsUser.getUsername());
        data.put("menus", roleService.getMenuList(umsUser.getId()));
        data.put("icon", umsUser.getIcon());
        List<OpfUpmRole> roleList = userService.getRoleList(umsUser.getId());
        if (CollUtil.isNotEmpty(roleList)) {
            List<String> roles = roleList.stream().map(OpfUpmRole::getName).collect(Collectors.toList());
            data.put("roles", roles);
        }
        return CommonResult.success(data);
    }

    @ApiOperation(value = "登出功能")
    @PostMapping(value = "/logout")
    public CommonResult logout() {
        return CommonResult.success("登出成功");
    }

    @ApiOperation("根据用户名或姓名分页获取用户列表")
    @GetMapping(value = "/list")
    public CommonResult<CommonPage<OpfUpmUser>> list(@RequestParam(value = "keyword", required = false) String keyword,
                                                     @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                                     @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        Page<OpfUpmUser> userList = userService.list(keyword, pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(userList));
    }

    @ApiOperation("获取指定用户信息")
    @GetMapping(value = "/{id}")
    public CommonResult<OpfUpmUser> getItem(@PathVariable Long id) {
        OpfUpmUser user = userService.getById(id);
        return CommonResult.success(user);
    }

    @ApiOperation("修改指定用户信息")
    @PostMapping(value = "/update/{id}")
    public CommonResult update(@PathVariable Long id, @RequestBody OpfUpmUser user) {
        boolean success = userService.update(id, user);
        if (success) {
            return CommonResult.success(null);
        }
        return CommonResult.failed();
    }

    @ApiOperation("修改指定用户密码")
    @PostMapping(value = "/updatePassword")
    public CommonResult updatePassword(@Validated @RequestBody UpdateOpfUpmUserPasswordParam updatePasswordParam) {
        ResultCode code = userService.updatePassword(updatePasswordParam);
        return CommonResult.resultCode(code, code.getMessage());
    }

    @ApiOperation("删除指定用户信息")
    @PostMapping(value = "/delete/{id}")
    public CommonResult delete(@PathVariable Long id) {
        boolean success = userService.delete(id);
        if (success) {
            return CommonResult.success(null);
        }
        return CommonResult.failed();
    }

    @ApiOperation("修改帐号状态")
    @PostMapping(value = "/updateStatus/{id}")
    public CommonResult updateStatus(@PathVariable Long id, @RequestParam(value = "status") Integer status) {
        OpfUpmUser umsUser = new OpfUpmUser();
        umsUser.setStatus(status);
        boolean success = userService.update(id, umsUser);
        if (success) {
            return CommonResult.success(null);
        }
        return CommonResult.failed();
    }

    @ApiOperation("给用户分配角色")
    @PostMapping(value = "/role/update")
    public CommonResult updateRole(@RequestParam("userId") Long userId,
                                   @RequestParam("roleIds") List<Long> roleIds) {
        int count = userService.updateRole(userId, roleIds);
        if (count >= 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("获取指定用户的角色")
    @GetMapping(value = "/role/{userId}")
    public CommonResult<List<OpfUpmRole>> getRoleList(@PathVariable Long userId) {
        List<OpfUpmRole> roleList = userService.getRoleList(userId);
        return CommonResult.success(roleList);
    }
}

