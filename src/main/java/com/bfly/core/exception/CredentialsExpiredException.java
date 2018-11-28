package com.bfly.core.exception;

/**
 * 认证信息过期异常。如：规定密码必须在一个月后修改，但是没有修改。
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/20 10:39
 */
public class CredentialsExpiredException extends AccountStatusException {
    public CredentialsExpiredException() {
    }

    public CredentialsExpiredException(String msg) {
        super(msg);
    }

    public CredentialsExpiredException(String msg, Object extraInformation) {
        super(msg, extraInformation);
    }
}
