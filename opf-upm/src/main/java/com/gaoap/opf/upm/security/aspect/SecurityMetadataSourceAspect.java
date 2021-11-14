package com.gaoap.opf.upm.security.aspect;

import com.gaoap.opf.upm.security.annotation.ReloadResource;
import com.gaoap.opf.upm.security.filter.handler.DynamicSecurityMetadataSource;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * SecurityMetadataSource切面，重新加载MetadataSource.如果不存在dynamicSecurityMetadataSource
 * 则此类不会被初始化
 */
@Aspect
@ConditionalOnBean(name = "dynamicSecurityMetadataSource")
@Component
@Order(2)
@Slf4j
public class SecurityMetadataSourceAspect {
    @Autowired(required = false)
    private DynamicSecurityMetadataSource dynamicSecurityMetadataSource;

    @Pointcut("execution(public * com.gaoap.opf.upm.service.*.OpfUpmResourceServiceImpl.*(..))")
    public void aspect() {
    }

    @Around("aspect()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        Object result = null;

        result = joinPoint.proceed();
        if (method.isAnnotationPresent(ReloadResource.class)) {
            try {
                dynamicSecurityMetadataSource.clearDataSource();
                log.info("dynamicSecurityMetadataSource 清空，原因是{}方法{}被调用", signature.getName(), method.getName());
            } catch (Throwable throwable) {
                log.error(throwable.getMessage());
            }

        }
        return result;
    }

}
