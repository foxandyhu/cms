package com.bfly.core.config;

import com.bfly.cms.member.entity.Member;
import com.bfly.core.context.MemberThreadLocal;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 货源操作日志AOP配置
 *
 * @author andy_hulibo@163.com
 * @date 2019/9/6 8:48
 */
@Aspect
@Component
public class MemberLogsAop extends AbstractLogsAop {

    /**
     * 定义日志切入点方法
     *
     * @author andy_hulibo@163.com
     * @date 2019/6/27 17:22
     */
    @Pointcut("execution(* com.bfly.web..*.*(..))")
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
        final Member member = MemberThreadLocal.get();
        super.beforeRequest(joinPoint, member == null ? null : member.getUserName(), true);
    }

    /**
     * 目标对象方法抛出异常后执行
     *
     * @author andy_hulibo@163.com
     * @date 2019/6/27 17:23
     */
    @AfterThrowing(pointcut = "pointcut()", throwing = "throwable")
    public void afterThrowingRequest(JoinPoint joinPoint, Throwable throwable) {
        final Member member = MemberThreadLocal.get();
        super.afterThrowingRequest(joinPoint, throwable, member == null ? null : member.getUserName(), true);
    }
}
