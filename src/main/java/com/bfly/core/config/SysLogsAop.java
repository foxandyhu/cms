package com.bfly.core.config;

import com.bfly.cms.logs.service.ISysLogService;
import com.bfly.cms.user.entity.User;
import com.bfly.common.reflect.ReflectUtils;
import com.bfly.core.context.IpThreadLocal;
import com.bfly.core.context.ServletRequestThreadLocal;
import com.bfly.core.context.UserThreadLocal;
import com.bfly.core.enums.LogsType;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * 系统日志AOP配置
 *
 * @author andy_hulibo@163.com
 * @date 2019/6/26 18:00
 */
@Aspect
@Component
public class SysLogsAop {

    @Autowired
    private ISysLogService sysLogService;

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
        if (!getNeedLog(joinPoint)) {
            return;
        }
        String title = getTitle(joinPoint);
        final HttpServletRequest request = ServletRequestThreadLocal.get();
        final String ip = IpThreadLocal.get();
        final User user = UserThreadLocal.get();
        final String url = request.getRequestURL().toString();
        savelogTask(LogsType.OP_LOG, user == null ? null : user.getUserName(), ip, url, title, null, true);
    }

    /**
     * 目标对象方法抛出异常后执行
     *
     * @author andy_hulibo@163.com
     * @date 2019/6/27 17:23
     */
    @AfterThrowing(pointcut = "pointcut()", throwing = "throwable")
    public void afterThrowingRequest(JoinPoint joinPoint, Throwable throwable) {
        final HttpServletRequest request = ServletRequestThreadLocal.get();
        final String ip = IpThreadLocal.get();
        final User user = UserThreadLocal.get();
        final String url = request.getRequestURL().toString();
        final String title = getTitle(joinPoint);
        savelogTask(LogsType.OP_LOG, user == null ? null : user.getUserName(), ip, url, title, throwable.getMessage(), false);
    }

    /**
     * 获得目标方法的描述
     *
     * @author andy_hulibo@163.com
     * @date 2019/6/27 14:20
     */
    private String getTitle(JoinPoint joinPoint) {
        String title = null;
        try {
            final Method method = getActionMethod(joinPoint);
            title = ReflectUtils.getModelDescription(method);
        } catch (Exception e) {
        }
        return title;
    }

    /**
     * 判断是否需要记录日志
     *
     * @author andy_hulibo@163.com
     * @date 2019/6/27 17:52
     */
    private boolean getNeedLog(JoinPoint joinPoint) {
        boolean flag = false;
        try {
            final Method method = getActionMethod(joinPoint);
            flag = ReflectUtils.getModelNeedLog(method);
        } catch (Exception e) {
        }
        return flag;
    }

    /**
     * 返回调用的方法
     *
     * @author andy_hulibo@163.com
     * @date 2019/6/27 17:52
     */
    private Method getActionMethod(JoinPoint joinPoint) throws Exception {
        final Class<?> c = joinPoint.getTarget().getClass();
        String methodName = joinPoint.getSignature().getName();
        Class<?>[] types = ((CodeSignature) joinPoint.getStaticPart().getSignature()).getParameterTypes();
        return c.getMethod(methodName, types);
    }

    /**
     * 保存日志
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/26 17:05
     */
    private void savelogTask(LogsType category, String userName, String ip, String url, String title, String content, boolean success) {
        sysLogService.save(category, userName, ip, url, title, content, success);
    }
}
