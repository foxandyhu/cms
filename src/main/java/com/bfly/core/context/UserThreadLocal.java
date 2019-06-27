package com.bfly.core.context;

import com.bfly.cms.user.entity.User;

/**
 * 用户User对象保存ThreadLocal
 *
 * @author andy_hulibo@163.com
 * @date 2019/6/26 18:43
 */
public class UserThreadLocal implements IThreadLocalContext {

    private static UserThreadLocal instance;
    private ThreadLocal<User> threadLocalUser = new ThreadLocal<>();

    private UserThreadLocal() {
    }

    private static synchronized UserThreadLocal getInstance() {
        if (instance == null) {
            instance = new UserThreadLocal();
        }
        return instance;
    }

    /**
     * 设置user到当前线程变量中
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/8 9:45
     */
    public static void set(User user) {
        getInstance().threadLocalUser.set(user);
    }

    /**
     * 从当前线程变量中获取user
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/8 9:46
     */
    public static User get() {
        return get(false);
    }

    /**
     * 从当前线程变量中获取User对象,并标识是否移除数据 false表示保留
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/8 9:46
     */
    public static User get(boolean isRemove) {
        User user = getInstance().getData(getInstance().threadLocalUser);
        if (!isRemove) {
            set(user);
        }
        return user;
    }

    /**
     * 清除数据
     *
     * @author andy_hulibo@163.com
     * @date 2019/6/27 16:16
     */
    public static void clear() {
        getInstance().threadLocalUser.remove();
    }
}
