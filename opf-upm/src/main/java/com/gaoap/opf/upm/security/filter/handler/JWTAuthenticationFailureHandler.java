package com.gaoap.opf.upm.security.filter.handler;

import com.alibaba.fastjson.JSONObject;
import com.gaoap.opf.upm.common.http.CommonResult;
import com.gaoap.opf.upm.common.http.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component("jwtAuthenticationFailureHandler")
public class JWTAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        log.error("登录认证失败", exception);
        CommonResult result = null;
        int status = 401;
        if (exception instanceof UsernameNotFoundException) {
            result = CommonResult.failed(ResultCode.VALIDATE_FAILED, "用户不存在");
        } else if (exception instanceof BadCredentialsException) {
            result = CommonResult.failed(ResultCode.UNAUTHORIZED, "用户名密码错误");
        } else {
            result = CommonResult.failed(ResultCode.FAILED, "未知错误，请检查用户名密码");
        }
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.setStatus(status);
        response.getWriter().write(JSONObject.toJSONString(result));
    }
}
