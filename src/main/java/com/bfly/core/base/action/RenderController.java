package com.bfly.core.base.action;

import com.bfly.cms.member.entity.Member;
import com.bfly.core.Constants;

/**
 * 页面渲染controller父类
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/29 11:46
 */
public class RenderController extends AbstractController {

    /**
     * 获得用户
     *
     * @return 用户
     * @author andy_hulibo@163.com
     * @date 2018/11/29 14:53
     */
    public Member getMember() {
        return (Member) getRequest().getAttribute(Constants.MEMBER_LOGIN_KEY);
    }

    /**
     * URL重定向
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/29 16:59
     */
    public String redirect(String url) {
        return "redirect:" + url;
    }
}
