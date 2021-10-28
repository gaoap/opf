package com.gaoap.opf.oauth.jwt.handler;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.common.utils.MD5Utils;
import com.gaoap.opf.common.core.http.HttpResult;
import com.gaoap.opf.common.core.jwt.JwtTokenUtil;
import com.gaoap.opf.oauth.entity.AuthUser;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Component("jwtAuthenticationSuccessHandler")
public class JWTAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Value("${jwt.expiration}")
    private Long expiration;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private StringRedisTemplate redisTemplate;

    @SneakyThrows
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        AuthUser user = (AuthUser) authentication.getPrincipal();

        /**
         * 生成token
         */
        String jwtToken = jwtTokenUtil.generateToken(user.getUsername());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        HttpResult result = HttpResult.ok();
        result.setData(jwtToken);
        //END

        //生成Key， 把权限放入到redis中
        String keyPrefix = "JWT" + user.getUsername() + ":";
        String keySuffix = MD5Utils.encodeHexString(jwtToken.trim().getBytes());
        String key = keyPrefix + keySuffix;

        String authKey = key + ":Authorities";
        System.out.println("---authKey--------------" + authKey);
        redisTemplate.opsForValue().set(key, jwtToken, expiration, TimeUnit.MILLISECONDS);
        redisTemplate.opsForValue().set(authKey, JSONObject.toJSONString(user.getAuthorities()), expiration, TimeUnit.SECONDS);
        System.out.println("---authKeyuser--------------" + JSONObject.toJSONString(user.getAuthorities()));
        response.getWriter().write(JSONObject.toJSONString(result));
    }
}
