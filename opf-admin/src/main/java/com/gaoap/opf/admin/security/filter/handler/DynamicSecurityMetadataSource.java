package com.gaoap.opf.admin.security.filter.handler;

import cn.hutool.core.util.URLUtil;
import com.gaoap.opf.admin.entity.SysResource;
import com.gaoap.opf.admin.security.ser.DynamicSecurityService;
import com.gaoap.opf.admin.service.ISysResourceService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 动态权限数据源，用于获取动态权限规则
 */
@ConditionalOnBean(name="dynamicSecurityFilter")
@Component
@Configuration
@Slf4j
public class DynamicSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    private static Map<String, ConfigAttribute> configAttributeMap = null;
    @Autowired
    private ISysResourceService sysResourceService;

    @Bean
    public DynamicSecurityService dynamicSecurityService() {
        return new DynamicSecurityService() {
            @Override
            public Map<String, ConfigAttribute> loadDataSource() {
                Map<String, ConfigAttribute> map = new ConcurrentHashMap<>();
                List<SysResource> resourceList = sysResourceService.list();
                for (SysResource resource : resourceList) {
                    if (!StringUtils.isEmpty(resource.getUrl()) && !StringUtils.isEmpty(resource.getPerms())) {
                        map.put(resource.getUrl(), new org.springframework.security.access.SecurityConfig(resource.getPerms()));

                    }
                }
                return map;
            }
        };
    }

    @PostConstruct
    public void loadDataSource() {
        configAttributeMap = dynamicSecurityService().loadDataSource();
    }

    public void clearDataSource() {
        configAttributeMap.clear();
        configAttributeMap = null;
    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        if (configAttributeMap == null) this.loadDataSource();
        configAttributeMap.entrySet().forEach(x -> log.info("DynamicSecurityMetadataSource:" + x.getValue()));
        List<ConfigAttribute> configAttributes = new ArrayList<>();
        //获取当前访问的路径
        String url = ((FilterInvocation) o).getRequestUrl();

        String path = URLUtil.getPath(url);
        log.info("url:{},path:{}", url, path);
        PathMatcher pathMatcher = new AntPathMatcher();
        Iterator<String> iterator = configAttributeMap.keySet().iterator();
        //获取访问该路径所需资源
        while (iterator.hasNext()) {
            String pattern = iterator.next();
            log.info("pattern:{},path:{},pathMatcher.match(pattern, path):{}", pattern, path, pathMatcher.match(pattern, path));
            if (pathMatcher.match(pattern, path)) {
                configAttributes.add(configAttributeMap.get(pattern));
            }
        }
        configAttributes.forEach(x -> log.info("configAttributes:{}", x.getAttribute()));
        // 未设置操作请求权限，返回空集合
        return configAttributes;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }

}
