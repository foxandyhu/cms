package com.bfly.core.interceptor;

import com.bfly.cms.member.entity.Member;
import com.bfly.core.context.*;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 会员中心拦截器
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/6 15:32
 */
@Component
public class MemberApiInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        Member member = getMember();
        IpThreadLocal.set(ContextUtil.getClientIp(request));
        ServletRequestThreadLocal.set(request);
        MemberThreadLocal.set(member);
        return true;
    }

    /**
     * 得到member
     *
     * @author andy_hulibo@163.com
     * @date 2019/9/6 20:51
     */
    private Member getMember() {
        return ContextUtil.getLoginMember();
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        IpThreadLocal.clear();
        ServletRequestThreadLocal.clear();
        MemberThreadLocal.clear();
        PagerThreadLocal.clear();
    }
}