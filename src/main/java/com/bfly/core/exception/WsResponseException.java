package com.bfly.core.exception;

import com.bfly.core.enums.SysError;

/**
 * 接口响应统一抛出异常
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/7 17:01
 */
public class WsResponseException extends RuntimeException {

    private String code;

    public WsResponseException(SysError error, String message) {
        super(message);
        error = error == null ? SysError.ERROR : error;
        this.code = error.getCode();
    }

    public String getCode() {
        return code;
    }
}
