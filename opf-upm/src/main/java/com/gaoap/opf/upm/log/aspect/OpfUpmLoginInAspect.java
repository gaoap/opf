package com.gaoap.opf.upm.log.aspect;

import com.gaoap.opf.upm.service.IOpfUpmUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Optional;


@Aspect
@Component
@Order(2)
@Slf4j
public class OpfUpmLoginInAspect {
    @Autowired
    private IOpfUpmUserService userService;

    @Pointcut("execution(public * com.gaoap.opf.upm.service.*.*User*.login(..))")
    public void aspect() {
    }

    @Around("aspect()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        Object result = null;
        result = joinPoint.proceed();
        try {
            String username = null;
            //参数名数组
            String[] parameters = methodSignature.getParameterNames();
            //参数值
            Object[] args = joinPoint.getArgs();
            //获取参数名对应数组下标
            int paramIndex = ArrayUtils.indexOf(parameters, "username");
            Optional<Object> opt = Optional.ofNullable(args[paramIndex]);
            username = opt.orElse("").toString();
            userService.updateLoginTimeByUsername(username);
            userService.insertLoginLog(username);
            log.info("更新{}:用户登录信息", username);
        } catch (Throwable throwable) {
//            log.error(throwable.getMessage());
            log.error("", throwable);
        }

        return result;
    }

}
