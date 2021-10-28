package com.gaoap.opf.admin.security.filter.handler;


import com.alibaba.fastjson.JSONObject;
import com.gaoap.opf.common.core.http.HttpResult;
import com.gaoap.opf.common.core.jwt.JwtTokenUtil;
import com.gaoap.opf.common.core.vo.TokenInfoVo;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component("jwtAuthenticationSuccessHandler")
@Slf4j
public class JWTAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @SneakyThrows
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UserDetails user = (UserDetails) authentication.getPrincipal();
        log.info("user:"+user.getUsername());
        log.info(jwtTokenUtil.toString());
        String jwtToken = jwtTokenUtil.generateToken(user.getUsername());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        HttpResult result = HttpResult.ok();
        result.setData(new TokenInfoVo(tokenHeader, tokenHead, jwtToken));
        response.getWriter().write(JSONObject.toJSONString(result));
    }
}
