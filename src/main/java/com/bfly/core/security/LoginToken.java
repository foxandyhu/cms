package com.bfly.core.security;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * 自定义Shiro 登录token
 *
 * @author andy_hulibo@163.com
 * @date 2019/9/17 16:00
 */
public class LoginToken extends UsernamePasswordToken {

    /**
     * 是否管理员登录
     *
     * @author andy_hulibo@163.com
     * @date 2019/9/17 16:01
     */
    private boolean isAdmin;

    public LoginToken(String userName, String password, boolean isRemember, boolean isAdmin) {
        super(userName, password, isRemember);
        this.isAdmin = isAdmin;
    }

    public boolean isAdmin() {
        return isAdmin;
    }
}
