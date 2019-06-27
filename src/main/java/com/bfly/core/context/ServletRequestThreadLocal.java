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
        return get(false);
    }

    /**
     * 从当前线程变量中获取HttpServletRequest,并标识是否移除数据 false表示保留 默认true
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/8 9:46
     */
    public static HttpServletRequest get(boolean isRemove) {
        HttpServletRequest request = getInstance().getData(getInstance().threadLocalServletRequest);
        if (!isRemove) {
            set(request);
        }
        return request;
    }

    /**
     * 清除数据
     *
     * @author andy_hulibo@163.com
     * @date 2019/6/27 16:16
     */
    public static void clear() {
        getInstance().threadLocalServletRequest.remove();
    }
}
