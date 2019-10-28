package com.bfly.core.config;

import com.bfly.cms.logs.service.ISysLogService;
import com.bfly.common.reflect.ReflectUtils;
import com.bfly.core.context.IpThreadLocal;
import com.bfly.core.context.ServletRequestThreadLocal;
import com.bfly.core.enums.LogsType;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * 操作日志抽象类父类
 *
 * @author andy_hulibo@163.com
 * @date 2019/9/6 8:48
 */
public abstract class AbstractLogsAop {

    @Autowired
    private ISysLogService sysLogService;


    /**
     * 目标对象方法调用之前执行
     *
     * @author andy_hulibo@163.com
     * @date 2019/6/27 17:22
     */
    public void beforeRequest(JoinPoint joinPoint, String userName, boolean isMember) {
        if (!getNeedLog(joinPoint)) {
            return;
        }
        String title = getTitle(joinPoint);
        final HttpServletRequest request = ServletRequestThreadLocal.get();
        final String ip = IpThreadLocal.get();
        final String url = request.getRequestURL().toString();
        saveLogTask(LogsType.OP_LOG, userName, ip, url, title, null, true, isMember);
    }

    /**
     * 目标对象方法抛出异常后执行
     *
     * @author andy_hulibo@163.com
     * @date 2019/6/27 17:23
     */
    public void afterThrowingRequest(JoinPoint joinPoint, Throwable throwable, String userName, boolean isMember) {
        final HttpServletRequest request = ServletRequestThreadLocal.get();
        final String ip = IpThreadLocal.get();
        final String url = request.getRequestURL().toString();
        final String title = getTitle(joinPoint);
        saveLogTask(LogsType.OP_LOG, userName, ip, url, title, throwable.getMessage(), false, isMember);
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
    private void saveLogTask(LogsType category, String userName, String ip, String url, String title, String content, boolean success, boolean isMember) {
        sysLogService.save(category, userName, ip, url, title, content, success, isMember);
    }
}
