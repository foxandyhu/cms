package com.bfly.core.exception;

/**
 * 未授权异常
 * @author andy_hulibo@163.com
 * @date 2019/6/24 11:58
 */
public class UnAuthException extends RuntimeException {

    public UnAuthException(String message) {
        super(message);
    }

}
