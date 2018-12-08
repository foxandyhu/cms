package com.bfly.core.exception;

/**
 * 接口响应统一抛出异常
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/7 17:01
 */
public class WsResponseException extends RuntimeException {

    private String code;

    public WsResponseException(String code, String message) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
