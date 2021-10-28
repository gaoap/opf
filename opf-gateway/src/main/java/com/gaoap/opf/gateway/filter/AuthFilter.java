package com.gaoap.opf.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.common.utils.MD5Utils;
import com.gaoap.opf.common.core.http.HttpResult;
import com.gaoap.opf.common.core.jwt.JwtTokenUtil;
import com.gaoap.opf.common.core.vo.Authority;

import com.gaoap.opf.gateway.config.ExclusionUrl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

import java.util.List;


@Component
@Slf4j
public class AuthFilter implements GlobalFilter, Ordered {
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Autowired
    private ExclusionUrl exclusionUrl;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        String headerToken = request.getHeaders().getFirst(tokenHeader);
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
        if (StringUtils.isEmpty(headerToken)) {
            return false;
        }
        String token = headerToken.replace(tokenHead, "");
        String username = jwtTokenUtil.getUserNameFromToken(token);
        //校验是否有效
        if (username == null) {
            log.error("token不合法，检测不过关");
            return false;
        }
        //校验超时

        if (!jwtTokenUtil.validateToken(token, username)) {
            log.error("token已经过期");
            return false;
        }
        //获取载体中的数据
        return true;

    }

    private boolean hasPermission(String headerToken, String path) {


        if (StringUtils.isEmpty(headerToken)) {
            return false;
        }
        String token = headerToken.replace(tokenHead, "");
        String username = jwtTokenUtil.getUserNameFromToken(token);
        log.info("---username：" + username);
        //生成Key， 把权限放入到redis中
        String keyPrefix = "JWT" + username + ":";

        String keySuffix = MD5Utils.encodeHexString(token.trim().getBytes());
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

    }


}
