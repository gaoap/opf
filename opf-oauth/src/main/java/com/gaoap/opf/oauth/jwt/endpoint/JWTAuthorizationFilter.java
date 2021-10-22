package com.gaoap.opf.oauth.jwt.endpoint;

import com.alibaba.fastjson.JSONObject;
import com.gaoap.opf.common.core.http.HttpResult;
import com.gaoap.opf.common.core.jwt.JWTConstants;
import com.gaoap.opf.common.core.vo.UserVo;
import com.gaoap.opf.oauth.entity.AuthUser;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.SignedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.Collection;
import java.util.Date;

/**
 * <p>鉴权</p>
 */
@Slf4j
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        String token = request.getHeader(JWTConstants.TOKEN_HEADER);
        log.info("doFilterInternal: 进入。。。。。 ");
        if (StringUtils.isEmpty(token) || !token.startsWith(JWTConstants.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }
        log.info("doFilterInternal: token。。。。。 " + token);
        try {
            Authentication authentication = getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            onSuccessfulAuthentication(request, response, authentication);
            chain.doFilter(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            onUnsuccessfulAuthentication(request, response, new AccountExpiredException(e.getMessage()));
        }
    }

    @Override
    protected void onSuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, Authentication authResult) throws IOException {
        log.info("Token 验证成功");
    }

    @Override
    protected void onUnsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        log.error("token校验失败");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        HttpResult result = HttpResult.error(HttpServletResponse.SC_UNAUTHORIZED, failed.getMessage());
        response.getWriter().write(JSONObject.toJSONString(result));
    }

    // 这里从token中获取用户信息并新建一个token
    private AbstractAuthenticationToken getAuthentication(String tokenHeader) throws ParseException, JOSEException {
        String token = tokenHeader.replace(JWTConstants.TOKEN_PREFIX, "");
        SignedJWT jwt = SignedJWT.parse(token);
        JWSVerifier verifier = new MACVerifier(JWTConstants.SECRET);
        //校验是否有效
        if (!jwt.verify(verifier)) {
            log.info("token:无效");
            throw new AccountExpiredException("Token 无效");
        }

        //校验超时
        Date expirationTime = jwt.getJWTClaimsSet().getExpirationTime();
        if (new Date().after(expirationTime)) {
            log.info("token:过期");
            throw new AccountExpiredException("Token 已过期");
        }

        //获取载体中的数据
        Object account = jwt.getJWTClaimsSet().getClaim("payload");
        if (account != null) {
            log.info("account："+account.toString());
            UserVo userVo = JSONObject.parseObject(account.toString(), UserVo.class);
            log.info("userVo："+userVo.toString());
            return new UsernamePasswordAuthenticationToken(userVo.getUsername(), userVo.getPassword(), null);
        }
        return null;
    }
}
