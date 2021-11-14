package com.gaoap.opf.upm.security.annotation;

import java.lang.annotation.*;

/**
 * 自定义注解，有该注解的方法，会触发DynamicSecurityMetadataSource.clearDataSource()
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ReloadResource {
}
