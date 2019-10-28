package com.bfly.core.config;

import com.bfly.cms.user.entity.User;
import com.bfly.core.context.UserThreadLocal;
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
public class SysLogsAop extends AbstractLogsAop {


    /**
     * 定义日志切入点方法
     *
     * @author andy_hulibo@163.com
     * @date 2019/6/27 17:22
     */
    @Pointcut("execution(* com.bfly.manage..*.*(..))")
    public void pointcut() {
    }

    /**
     * 目标对象方法调用之前执行
     *
     * @author andy_hulibo@163.com
     * @date 2019/6/27 17:22
     */
    @Before("pointcut()")
    public void beforeRequest(JoinPoint joinPoint) {
        final User user = UserThreadLocal.get();
        super.beforeRequest(joinPoint, user == null ? null : user.getUserName(), false);
    }

    /**
     * 目标对象方法抛出异常后执行
     *
     * @author andy_hulibo@163.com
     * @date 2019/6/27 17:23
     */
    @AfterThrowing(pointcut = "pointcut()", throwing = "throwable")
    public void afterThrowingRequest(JoinPoint joinPoint, Throwable throwable) {
        final User user = UserThreadLocal.get();
        super.afterThrowingRequest(joinPoint, throwable, user == null ? null : user.getUserName(), false);
    }
}
