package com.gaoap.opf.admin.controller;


import com.gaoap.opf.common.core.http.HttpResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 登陆实现的一种方式，暂时没有使用此方法。使用的springboot secrutiy自带的登陆接口
 */
//@RestController
//@RequestMapping("/admin")
public class OpfAdminLoginController {


    @ApiOperation(value = "登录以后返回token")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public HttpResult login(HttpServletRequest request) {
        String tokenHead = "";
        String token = null;
        if (token == null) {
            return HttpResult.unauthorized("用户名或密码错误");
        }
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return HttpResult.ok(tokenMap);
    }

    @ApiOperation(value = "刷新token")
    @RequestMapping(value = "/refreshToken", method = RequestMethod.GET)
    @ResponseBody
    public HttpResult refreshToken(HttpServletRequest request) {
        String tokenHeader = "";
        String tokenHead = "";
        String token = request.getHeader(tokenHeader);
        String refreshToken = null;
        if (refreshToken == null) {
            return HttpResult.forbidden("token已经过期！");
        }
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", refreshToken);
        tokenMap.put("tokenHead", tokenHead);
        return HttpResult.ok(tokenMap);
    }


    @ApiOperation(value = "登出功能")
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ResponseBody
    public HttpResult logout() {
        return HttpResult.ok(null);
    }


}
