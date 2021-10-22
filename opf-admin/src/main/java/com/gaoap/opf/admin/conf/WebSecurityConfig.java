package com.gaoap.opf.admin.conf;


//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Spring Security Config
 *
 * @author Louis
 * @date Nov 20, 2018
 */
//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        // 禁用 csrf, 由于使用的是JWT，我们这里不需要csrf
//        http.cors().and().csrf().disable()
//                .authorizeRequests()
//                // 跨域预检请求
//                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
//                // web jars
//                .antMatchers("/webjars/**").permitAll()
//                // 查看SQL监控（druid）
//                .antMatchers("/druid/**").permitAll()
//                // 首页和登录页面
//                .antMatchers("/").permitAll()
//                .antMatchers("/login").permitAll()
//                .antMatchers("/index.html").permitAll()
//                .antMatchers("/static/**").permitAll()
//                // swagger
//                .antMatchers("/**/swagger-ui/**").permitAll()
//                .antMatchers("/**/swagger-resources/**").permitAll()
//                .antMatchers("/**/v3/**").permitAll()
//                .anyRequest().permitAll();
//
//
//    }
//}