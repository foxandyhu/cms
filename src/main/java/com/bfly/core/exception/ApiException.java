package com.bfly.core.exception;

import com.bfly.core.web.ApiResponse;

/**
 * API接口调用异常
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/3 16:48
 */
public class ApiException extends RuntimeException {

    private ApiResponse response;

    public ApiException(String message, String code) {
        response = new ApiResponse(message, code);
    }

    public ApiResponse getResponse() {
        return response;
    }

    public void setResponse(ApiResponse response) {
        this.response = response;
    }
}
