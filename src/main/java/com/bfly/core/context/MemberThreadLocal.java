package com.bfly.core.context;

import com.bfly.cms.member.entity.Member;

/**
 * 用户Member对象保存ThreadLocal
 *
 * @author andy_hulibo@163.com
 * @date 2019/8/28 9:45
 */
public class MemberThreadLocal implements IThreadLocalContext {

    private static MemberThreadLocal instance;
    private ThreadLocal<Member> threadLocalMember = new ThreadLocal<>();

    private MemberThreadLocal() {
    }

    private static synchronized MemberThreadLocal getInstance() {
        if (instance == null) {
            instance = new MemberThreadLocal();
        }
        return instance;
    }

    /**
     * 设置Member到当前线程变量中
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/8 9:45
     */
    public static void set(Member member) {
        getInstance().threadLocalMember.set(member);
    }

    /**
     * 从当前线程变量中获取member
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/8 9:46
     */
    public static Member get() {
        return get(false);
    }

    /**
     * 从当前线程变量中获取member对象,并标识是否移除数据 false表示保留
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/8 9:46
     */
    public static Member get(boolean isRemove) {
        Member member = getInstance().getData(getInstance().threadLocalMember);
        if (!isRemove) {
            set(member);
        }
        return member;
    }

    /**
     * 清除数据
     *
     * @author andy_hulibo@163.com
     * @date 2019/6/27 16:16
     */
    public static void clear() {
        getInstance().threadLocalMember.remove();
    }
}
