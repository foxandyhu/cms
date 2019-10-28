package com.bfly.core.exception;

import org.apache.shiro.authc.AuthenticationException;

/**
 * 账户未激活异常
 *
 * @author andy_hulibo@163.com
 * @date 2019/9/25 11:47
 */
public class AccountUnActiveException extends AuthenticationException {

    public AccountUnActiveException(String message) {
        super(message);
    }

}
