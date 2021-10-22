package com.gaoap.opf.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.common.utils.MD5Utils;
import com.gaoap.opf.common.core.http.HttpResult;
import com.gaoap.opf.common.core.jwt.JWTConstants;
import com.gaoap.opf.common.core.vo.Authority;
import com.gaoap.opf.common.core.vo.SysUser;
import com.gaoap.opf.common.core.vo.UserVo;
import com.gaoap.opf.gateway.config.ExclusionUrl;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.SignedJWT;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.text.ParseException;
import java.util.Date;
import java.util.List;


@Component
@Slf4j
public class AuthFilter implements GlobalFilter, Ordered {

    @Autowired
    private ExclusionUrl exclusionUrl;

    AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        String headerToken = request.getHeaders().getFirst(JWTConstants.TOKEN_HEADER);
        log.info("headerToken:{}", headerToken);
        //1、只要带上了token， 就需要判断Token是否有效
        if (!StringUtils.isEmpty(headerToken) && !verifierToken(headerToken)) {
            return getVoidMono(response, 401, "token无效");
        }
        String path = request.getURI().getPath();
        log.info("request path:{}", path);
        //2、判断是否是过滤的路径， 是的话就放行
        if (isExclusionUrl(path)) {
            return chain.filter(exchange);
        }
        //3、判断请求的URL是否有权限
        boolean permission = hasPermission(headerToken, path);
        if (!permission) {
            return getVoidMono(response, 403, "无访问权限");
        }
        return chain.filter(exchange);
    }


    @Override
    public int getOrder() {
        return 0;
    }

    private Mono<Void> getVoidMono(ServerHttpResponse response, int i, String msg) {
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        response.setStatusCode(HttpStatus.OK);
        HttpResult failed = HttpResult.error(i, msg);
        byte[] bits = JSONObject.toJSONString(failed).getBytes();
        DataBuffer buffer = response.bufferFactory().wrap(bits);
        return response.writeWith(Mono.just(buffer));
    }

    private boolean isExclusionUrl(String path) {
        List<String> exclusions = exclusionUrl.getUrl();
        if (exclusions.size() == 0) {
            return false;
        }
        return exclusions.stream().anyMatch(action -> antPathMatcher.match(action, path));

    }

    private boolean verifierToken(String headerToken) {
        try {
            SignedJWT jwt = getSignedJWT(headerToken);
            JWSVerifier verifier = new MACVerifier(JWTConstants.SECRET);
            //校验是否有效
            if (!jwt.verify(verifier)) {
                log.error("token不合法，检测不过关");
                return false;
            }
            //校验超时
            Date expirationTime = jwt.getJWTClaimsSet().getExpirationTime();
            if (new Date().after(expirationTime)) {
                log.error("token已经过期");
                return false;
            }
            //获取载体中的数据
            return true;
        } catch (ParseException | JOSEException e) {
            log.error("token校验出错", e);
        }
        return false;
    }

    private boolean hasPermission(String headerToken, String path) {
        try {
            if (StringUtils.isEmpty(headerToken)) {
                return false;
            }
            SignedJWT jwt = getSignedJWT(headerToken);
            Object payload = jwt.getJWTClaimsSet().getClaim("payload");
            UserVo userVo = JSONObject.parseObject(payload.toString(), UserVo.class);
            Long userId = userVo.getUserId();
            log.info("---userId：" + userId);
            //生成Key， 把权限放入到redis中
            String keyPrefix = "JWT" + userId + ":";
            String token = headerToken.replace(JWTConstants.TOKEN_PREFIX, "");
            String keySuffix = MD5Utils.encodeHexString(token.getBytes());
            String key = keyPrefix + keySuffix;
            String authKey = key + ":Authorities";
            log.info("---authKey：" + authKey);
            String authStr = redisTemplate.opsForValue().get(authKey);
            if (StringUtils.isEmpty(authStr)) {
                return false;
            }
            List<Authority> authorities = JSON.parseArray(authStr, Authority.class);
            log.info("-----------------authorities.size：" + authorities.size());
            authorities.forEach(x -> System.out.println(x.getAuthority()));
            return authorities.stream().anyMatch(authority -> antPathMatcher.match(authority.getAuthority(), path));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    private SignedJWT getSignedJWT(String headerToken) throws ParseException {
        String token = headerToken.replace(JWTConstants.TOKEN_PREFIX, "");
        log.info("token is {}", token);
        return SignedJWT.parse(token);
    }
}
