package com.gaoap.opf.upm.conf;

import com.gaoap.opf.upm.entity.OpfUpmResource;
import com.gaoap.opf.upm.security.jwt.JwtTokenUtil;
import com.gaoap.opf.upm.security.ser.DynamicSecurityService;
import com.gaoap.opf.upm.service.IOpfUpmResourceService;
import com.gaoap.opf.upm.service.IOpfUpmUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author gaoyd
 * @version 1.0.0
 * @ClassName BeanConfig.java
 * @Description TODO
 * @createTime 2021年10月27日 17:44:00
 */
@Configuration
public class GlobalBeanConfig {
    //token 前缀
    @Value("${opf.upm.jwt.tokenHead}")
    private String tokenHead;
    //jwt加密密钥
    @Value("${opf.upm.jwt.secret}")
    private String secret;
    //jwt有效时长，单位ms
    @Value("${opf.upm.jwt.expiration}")
    private Long expiration;
    @Autowired
    private IOpfUpmUserService userService;
    @Autowired
    private IOpfUpmResourceService resourceService;

    @Bean
    public JwtTokenUtil jwtTokenUtil() {
        return new JwtTokenUtil(secret, expiration, tokenHead);
    }

    @Bean
    public UserDetailsService userDetailsService() {

        //获取登录用户信息
        return username -> userService.loadUserByUsername(username);
    }

    //Spring Security 推荐加密算法
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //加载系统配置中所有需要保护的资源
    //存在BeanName=“dynamicSecurityFilter”才允许初始化DynamicSecurityMetadataSource
   // @ConditionalOnBean(name = "dynamicSecurityFilter")
    @ConditionalOnProperty(name = "opf.upm.security.dynamic", havingValue = "true", matchIfMissing = false)
    @Bean
    public DynamicSecurityService dynamicSecurityService() {
        return new DynamicSecurityService() {
            @Override
            public Map<String, ConfigAttribute> loadDataSource() {
                Map<String, ConfigAttribute> map = new ConcurrentHashMap<>();
                List<OpfUpmResource> resourceList = resourceService.list();
                for (OpfUpmResource resource : resourceList) {
                    map.put(resource.getUrl(), new SecurityConfig(resource.getId() + ":" + resource.getName()));
                }
                return map;
            }
        };
    }

}
