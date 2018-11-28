package com.bfly.core.exception;

/**
 * @author andy_hulibo@163.com
 * @date 2018/11/27 14:52
 * 账号状态异常
 */
public class AccountStatusException extends AuthenticationException {
    public AccountStatusException() {
    }

    public AccountStatusException(String msg) {
        super(msg);
    }

    public AccountStatusException(String msg, Object extraInformation) {
        super(msg, extraInformation);
    }
}
