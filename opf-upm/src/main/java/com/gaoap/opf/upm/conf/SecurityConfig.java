package com.gaoap.opf.upm.conf;

import com.gaoap.opf.upm.security.filter.DynamicSecurityFilter;
import com.gaoap.opf.upm.security.filter.JwtAuthenticationTokenFilter;
import com.gaoap.opf.upm.security.filter.handler.*;
import com.gaoap.opf.upm.security.jwt.JwtTokenUtil;
import com.gaoap.opf.upm.security.ser.DynamicSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


/**
 * 对SpringSecurity的配置的扩展，支持自定义白名单资源路径和查询用户逻辑
 * configure(HttpSecurity httpSecurity)：用于配置需要拦截的url路径、jwt过滤器及出异常后的处理器；
 * configure(AuthenticationManagerBuilder auth)：用于配置UserDetailsService及PasswordEncoder；
 * RestfulAccessDeniedHandler：当用户没有访问权限时的处理器，用于返回JSON格式的处理结果；
 * RestAuthenticationEntryPoint：当未登录或token失效时，返回JSON格式的结果；
 * UserDetailsService:SpringSecurity定义的核心接口，用于根据用户名获取用户信息，需要自行实现；
 * UserDetails：SpringSecurity定义用于封装用户信息的类（主要是用户信息和权限），需要自行实现；
 * PasswordEncoder：SpringSecurity定义的用于对密码进行编码及比对的接口，目前使用的是BCryptPasswordEncoder；
 * JwtAuthenticationTokenFilter：在用户名和密码校验前添加的过滤器，如果有jwt的token，会自行根据token信息进行登录。
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired(required = false)
    private DynamicSecurityMetadataSource dynamicSecurityMetadataSource;
    @Autowired(required = false)
    public DynamicAccessDecisionManager dynamicAccessDecisionManager;
    @Autowired
    private IgnoreUrlsConfig ignoreUrlsConfig;
    @Autowired
    public PasswordEncoder passwordEncoder;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    public RestfulAccessDeniedHandler restfulAccessDeniedHandler;
    @Autowired
    public RestAuthenticationEntryPoint restAuthenticationEntryPoint;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Value("${opf.upm.jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${opf.upm.jwt.tokenHead}")
    private String tokenHead;
    @Value("${opf.upm.security.dynamic:false}")
    private boolean dynamic = false;
    @Autowired
    public JWTAuthenticationSuccessHandler jwtAuthenticationSuccessHandler;
    @Autowired
    public JWTAuthenticationFailureHandler jwtAuthenticationFailureHandler;
    private static final String[] AUTH_WHITELIST = {

            // -- swagger ui
            "/**/swagger-ui/**",
            "/**/swagger-resources/**",
            "/**/v3/**",
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/v3/**",
            "/webjars/**"
    };

    /**
     * web ignore比较适合配置前端相关的静态资源，
     * 它是完全绕过spring security的所有filter的；
     * permitAll，会给没有登录的用户适配一个AnonymousAuthenticationToken，
     * 设置到SecurityContextHolder，方便后面的filter可以统一处理authentication
     * 需要注意的是：如果JwtAuthenticationTokenFilter类是spring容器初始化的，则
     * web.ignoring().antMatchers(AUTH_WHITELIST) 不能生效。
     * 如果是new 出来的，会应用  web.ignoring().antMatchers(AUTH_WHITELIST)配置
     */
    @Override
    public void configure(WebSecurity web) throws Exception {

        web.ignoring().
                antMatchers(AUTH_WHITELIST);
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = httpSecurity
                .authorizeRequests();
        //不需要保护的资源路径允许访问
        for (String url : ignoreUrlsConfig.getUrls()) {
            registry.antMatchers(url).permitAll();
        }
        registry.antMatchers(AUTH_WHITELIST).permitAll();
        //允许跨域请求的OPTIONS请求
        registry.antMatchers(HttpMethod.OPTIONS)
                .permitAll();
        // 任何请求需要身份认证
        registry.and()
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                // 关闭跨站请求防护及不使用session
                .and()
                //关闭csrf机制
                .csrf()
                .disable()
                //关闭session
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                // 自定义权限拒绝处理类
                .and()
                .exceptionHandling()
                //自定义返回结果：没有权限访问时
                .accessDeniedHandler(restfulAccessDeniedHandler)
                //自定义返回结果：未登录或登录过期
                .authenticationEntryPoint(restAuthenticationEntryPoint)
                // 自定义权限拦截器JWT过滤器
                .and()
                //通过JwtAuthenticationTokenFilter检查jwt token是否合法
                .addFilterBefore(new JwtAuthenticationTokenFilter(userDetailsService, jwtTokenUtil, tokenHeader, tokenHead), UsernamePasswordAuthenticationFilter.class);
        //有动态权限配置时添加动态权限校验过滤器 DynamicSecurityFilter
        if (dynamic) {
            registry.and().addFilterBefore(new DynamicSecurityFilter(dynamicSecurityMetadataSource,
                    ignoreUrlsConfig, dynamicAccessDecisionManager), FilterSecurityInterceptor.class);
        }

    }

    /**
     * userDetailsService 主要提供通过用户名，获取UserDetails的实现类
     * UserDetails：SpringSecurity定义用于封装用户信息的类（主要是用户信息和权限），需要自行实现；
     * passwordEncoder 用户密码加密算法。例如：BCryptPasswordEncoder
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }
}
