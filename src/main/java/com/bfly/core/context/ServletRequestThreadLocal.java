package com.bfly.core.context;

import javax.servlet.http.HttpServletRequest;

/**
 * HttpServletRequest对象保存ThreadLocal
 *
 * @author andy_hulibo@163.com
 * @date 2019/6/26 18:43
 */
public class ServletRequestThreadLocal implements IThreadLocalContext {

    private static ServletRequestThreadLocal instance;
    private ThreadLocal<HttpServletRequest> threadLocalServletRequest = new ThreadLocal<>();

    private ServletRequestThreadLocal() {
    }

    private static synchronized ServletRequestThreadLocal getInstance() {
        if (instance == null) {
            instance = new ServletRequestThreadLocal();
        }
        return instance;
    }

    /**
     * 设置HttpServletRequest到当前线程变量中
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/8 9:45
     */
    public static void set(HttpServletRequest request) {
        getInstance().threadLocalServletRequest.set(request);
    }

    /**
     * 从当前线程变量中获取HttpServletRequest
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/8 9:46
     */
    public static HttpServletRequest get() {
        return getInstance().getData(getInstance().threadLocalServletRequest);
    }
}
