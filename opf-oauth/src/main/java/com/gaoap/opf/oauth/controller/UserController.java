package com.gaoap.opf.oauth.controller;

import com.gaoap.opf.common.core.http.HttpResult;
import com.gaoap.opf.oauth.service.OpfAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@RestController
public class UserController {
    @Autowired
    private OpfAdminService opfAdminService;

    @RequestMapping("/getUser/{username}")
    public HttpResult user(@PathVariable("username") String username) {

        return opfAdminService.findByUsername(username);
    }

    @RequestMapping("checkToken")
    public HttpResult checkToken(HttpServletRequest request) {
        return HttpResult.ok("token有效");
    }
}
