package com.bfly.core.exception;

/**
 * 没有访问权限异常
 * @author andy_hulibo@163.com
 * @date 2019/7/13 19:12
 */
public class UnRightException extends RuntimeException {

    public UnRightException(String message) {
        super(message);
    }

}
