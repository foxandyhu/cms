package com.bfly.core.context;

/**
 * 用来标识用户是否自动登录的ThreadLocal
 * 由于用户登录密码是密文存储,shiro登录会对密码进行加密,所有使用该类来标识是否是自动登录
 * 如果是自动登录 则将数据库中的密文密码 再次加密 后和 当前shiro存储的密文密码进行对比
 *
 * @author andy_hulibo@163.com
 * @date 2019/9/17 14:51
 */
public class AutoLoginThreadLocal implements IThreadLocalContext {

    private static AutoLoginThreadLocal instance;
    private ThreadLocal<Boolean> threadLocalAutoLogin = new ThreadLocal<>();

    private AutoLoginThreadLocal() {
    }

    private static synchronized AutoLoginThreadLocal getInstance() {
        if (instance == null) {
            instance = new AutoLoginThreadLocal();
        }
        return instance;
    }

    /**
     * 设置boolean到当前线程变量中
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/8 9:45
     */
    public static void set(Boolean isAutoLogin) {
        getInstance().threadLocalAutoLogin.set(isAutoLogin);
    }

    /**
     * 从当前线程变量中获取标识对象
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/8 9:46
     */
    public static Boolean get() {
        Boolean isAutoLogin;
        try {
            isAutoLogin = getInstance().getData(getInstance().threadLocalAutoLogin);
        } finally {
            clear();
        }
        return isAutoLogin;
    }

    /**
     * 清除数据
     *
     * @author andy_hulibo@163.com
     * @date 2019/6/27 16:16
     */
    public static void clear() {
        getInstance().threadLocalAutoLogin.remove();
    }
}
