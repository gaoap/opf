package com.gaoap.opf.upm.conf;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 指定mapper的路径，如：src/main/java/com/gaoap/opf/upm/mapper
 * mapper类上要增加@Mapper注解，否则可能会出现找不到mapper的情况。
 * 映射xml文件，如果不放在mapper目录下，如要配置：路径,例如：
 */
// mybatis-plus.mapper-locations=classpath: **/*Mapper.xml

@EnableTransactionManagement
@Configuration
@MapperScan(basePackages = {"com.gaoap.opf.upm.mapper"})//, annotationClass = Mapper.class)
public class MybatisPlusConfig {
    // MybatisPlus分页增强插件
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }
}