package com.gaoap.opf.upm.security.filter;

import com.gaoap.opf.upm.conf.IgnoreUrlsConfig;
import com.gaoap.opf.upm.security.filter.handler.DynamicAccessDecisionManager;
import com.gaoap.opf.upm.security.filter.handler.DynamicSecurityMetadataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


/**
 * 动态权限过滤器，用于实现基于路径的动态权限过滤
 * 参数：opf-upm.security.dynamic=true时，才会被初始化
 */
//@ConditionalOnProperty(name = "opf.upm.security.dynamic", havingValue = "true", matchIfMissing = false)
//@Component
@Slf4j
public class DynamicSecurityFilter extends AbstractSecurityInterceptor implements Filter {

    // @Autowired
    private DynamicSecurityMetadataSource dynamicSecurityMetadataSource;
    // @Autowired
    private IgnoreUrlsConfig ignoreUrlsConfig;

    //@Autowired
    public void setMyAccessDecisionManager(DynamicAccessDecisionManager dynamicAccessDecisionManager) {
        super.setAccessDecisionManager(dynamicAccessDecisionManager);
    }

    public DynamicSecurityFilter(DynamicSecurityMetadataSource dynamicSecurityMetadataSource,
                                 IgnoreUrlsConfig ignoreUrlsConfig, DynamicAccessDecisionManager dynamicAccessDecisionManager) {
        this.dynamicSecurityMetadataSource = dynamicSecurityMetadataSource;
        this.ignoreUrlsConfig = ignoreUrlsConfig;
        this.setMyAccessDecisionManager(dynamicAccessDecisionManager);

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("进入DynamicSecurityFilter：{}", servletRequest.getLocalAddr());
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        FilterInvocation fi = new FilterInvocation(servletRequest, servletResponse, filterChain);
        //OPTIONS请求直接放行
        if (request.getMethod().equals(HttpMethod.OPTIONS.toString())) {
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
            return;
        }
        //白名单请求直接放行
        PathMatcher pathMatcher = new AntPathMatcher();
        for (String path : ignoreUrlsConfig.getUrls()) {
            if (pathMatcher.match(path, request.getRequestURI())) {
                fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
                return;
            }
        }
        //此处会调用AccessDecisionManager中的decide方法进行鉴权操作
        InterceptorStatusToken token = super.beforeInvocation(fi);
        try {
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
        } finally {
            super.afterInvocation(token, null);
        }
    }

    @Override
    public void destroy() {
    }

    @Override
    public Class<?> getSecureObjectClass() {
        return FilterInvocation.class;
    }

    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return dynamicSecurityMetadataSource;
    }

}
