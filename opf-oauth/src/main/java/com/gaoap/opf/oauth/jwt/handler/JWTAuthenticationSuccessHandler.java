package com.gaoap.opf.oauth.jwt.handler;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.common.utils.MD5Utils;
import com.gaoap.opf.common.core.http.HttpResult;
import com.gaoap.opf.common.core.jwt.JWTConstants;
import com.gaoap.opf.common.core.vo.UserVo;
import com.gaoap.opf.oauth.entity.AuthUser;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component("jwtAuthenticationSuccessHandler")
public class JWTAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    /**
     * 过期时间2小时
     */
    private static final long EXPIRE_TIME = 1000 * 60 * 60 * 2;
    @Autowired
    private StringRedisTemplate redisTemplate;

    @SneakyThrows
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        AuthUser user = (AuthUser) authentication.getPrincipal();
        /**
         * 1、创建密钥
         */
        MACSigner macSigner = new MACSigner(JWTConstants.SECRET);
        /**
         * 2、payload
         */
        // String payload = JSONObject.toJSONString(user);
        Long userId = user.getId();
        String username=user.getUsername();
        String password= user.getPassword();
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject("subject")
                .claim("payload", JSONObject.toJSON(new UserVo(userId,username,password)))
                .expirationTime(new Date(System.currentTimeMillis() + EXPIRE_TIME))
                .build();
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS256);
        /**
         * 3、创建签名的JWT
         */
        SignedJWT signedJWT = new SignedJWT(jwsHeader, claimsSet);
        signedJWT.sign(macSigner);
        /**
         * 4、生成token
         */
        String jwtToken = signedJWT.serialize();
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        HttpResult result = HttpResult.ok();
        result.setData(jwtToken);
        //END

        //生成Key， 把权限放入到redis中
        String keyPrefix = "JWT" + userId + ":";
        String keySuffix = MD5Utils.encodeHexString(jwtToken.getBytes());
        String key = keyPrefix + keySuffix;

        String authKey = key + ":Authorities";
        System.out.println("---authKey--------------"+authKey);
        redisTemplate.opsForValue().set(key, jwtToken, EXPIRE_TIME, TimeUnit.MILLISECONDS);

        redisTemplate.opsForValue().set(authKey, JSONObject.toJSONString(user.getAuthorities()), EXPIRE_TIME, TimeUnit.SECONDS);
        System.out.println("---authKeyuser--------------"+JSONObject.toJSONString(user.getAuthorities()));
        response.getWriter().write(JSONObject.toJSONString(result));
    }
}
