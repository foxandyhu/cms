package com.bfly.core.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 系统日志AOP配置
 *
 * @author andy_hulibo@163.com
 * @date 2019/6/26 18:00
 */
@Aspect
@Component
public class SysLogsAop {

    @Pointcut("execution(* com.bfly.manage..*.*(..))")
    public void pointcut() {
    }

    @Before("pointcut()")
    public void beforeRequest(JoinPoint joinPoint){
        System.out.println("切入之前AOP "+joinPoint.getSignature().getName());
    }

    @AfterThrowing("pointcut()")
    public void afterThrowingRequest(){
        System.out.println("发生异常AOP");
    }
}
