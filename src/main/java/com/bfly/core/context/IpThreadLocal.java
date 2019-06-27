package com.bfly.core.context;

/**
 * IP保存ThreadLocal
 *
 * @author andy_hulibo@163.com
 * @date 2019/6/26 18:43
 */
public class IpThreadLocal implements IThreadLocalContext {

    private static IpThreadLocal instance;
    private ThreadLocal<String> threadLocalIp = new ThreadLocal<>();

    private IpThreadLocal() {
    }

    private static synchronized IpThreadLocal getInstance() {
        if (instance == null) {
            instance = new IpThreadLocal();
        }
        return instance;
    }

    /**
     * 设置IP到当前线程变量中
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/8 9:45
     */
    public static void set(String ip) {
        getInstance().threadLocalIp.set(ip);
    }

    /**
     * 从当前线程变量中获取IP
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/8 9:46
     */
    public static String get() {
        return getInstance().getData(getInstance().threadLocalIp);
    }
}
