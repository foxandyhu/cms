package com.bfly.core.exception;

import com.bfly.core.enums.SysError;

/**
 * web响应异常类
 *
 * @author andy_hulibo@163.com
 * @date 2019/9/18 11:05
 */
public class WebResponseException extends RuntimeException {

    private String code;

    public WebResponseException(SysError error, String message) {
        super(message);
        error = error == null ? SysError.ERROR : error;
        this.code = error.getCode();
    }

    public String getCode() {
        return code;
    }
}
