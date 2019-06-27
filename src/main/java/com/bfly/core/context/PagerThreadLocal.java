package com.bfly.core.context;

import com.bfly.common.DataConvertUtils;
import com.bfly.common.page.Pager;

import javax.servlet.http.HttpServletRequest;

/**
 * 分页对象保存ThreadLocal
 *
 * @author andy_hulibo@163.com
 * @date 2019/6/26 18:43
 */
public class PagerThreadLocal implements IThreadLocalContext {

    private static PagerThreadLocal instance;
    private ThreadLocal<Pager> threadLocalPager = new ThreadLocal<>();

    private PagerThreadLocal() {
    }

    private static synchronized PagerThreadLocal getInstance() {
        if (instance == null) {
            instance = new PagerThreadLocal();
        }
        return instance;
    }

    /**
     * 初始化分页器
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/8 20:22
     */
    public static void set(HttpServletRequest request) {
        set(request, Pager.DEF_COUNT);
    }

    /**
     * 实例化Pager对象
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/8 20:28
     */
    public static void set(HttpServletRequest request, int rows) {
        int page = DataConvertUtils.convertToInteger(request.getParameter("pageNo"));
        set(new Pager(page, rows, Integer.MAX_VALUE));
    }

    /**
     * 把分页对象放到当前线程中
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/8 20:09
     */
    public static void set(Pager pager) {
        getInstance().threadLocalPager.set(pager);
    }

    /**
     * 得到当前线程变量中的分页对象
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/8 20:10
     */
    public static Pager get() {
        return get(false);
    }

    /**
     * 从当前线程变量中获取分页对象,并标识是否移除数据 false表示保留 默认true
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/8 9:46
     */
    public static Pager get(boolean isRemove) {
        Pager pager = getInstance().getData(getInstance().threadLocalPager);
        if (!isRemove) {
            set(pager);
        }
        return pager;
    }

    /**
     * 清除数据
     *
     * @author andy_hulibo@163.com
     * @date 2019/6/27 16:16
     */
    public static void clear() {
        getInstance().threadLocalPager.remove();
    }
}
