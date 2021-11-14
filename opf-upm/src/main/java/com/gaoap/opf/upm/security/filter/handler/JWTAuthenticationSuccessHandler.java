package com.gaoap.opf.upm.security.filter.handler;


import com.alibaba.fastjson.JSONObject;
import com.gaoap.opf.upm.common.http.CommonResult;
import com.gaoap.opf.upm.security.jwt.JwtTokenUtil;
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
import java.util.HashMap;
import java.util.Map;

@Component("jwtAuthenticationSuccessHandler")
@Slf4j
public class JWTAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Value("${opf.upm.jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${opf.upm.jwt.tokenHead}")
    private String tokenHead;

    @SneakyThrows
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UserDetails user = (UserDetails) authentication.getPrincipal();
        log.info("user:" + user.getUsername());
        log.info(jwtTokenUtil.toString());
        String jwtToken = jwtTokenUtil.generateToken(user.getUsername());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        Map<String, String> tokenMap = new HashMap<>();
        //具体的token内容
        tokenMap.put("token", jwtToken);
        // token的前缀
        tokenMap.put("tokenHead", tokenHead);
        //headers中储存token的key
        tokenMap.put("tokenHeader", tokenHeader);
        response.getWriter().write(JSONObject.toJSONString(CommonResult.success(tokenMap)));
    }
}
