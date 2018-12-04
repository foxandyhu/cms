package com.bfly.core.web;

import com.bfly.common.web.springmvc.MessageResolver;
import com.bfly.core.Constants;

import javax.servlet.http.HttpServletRequest;

/**
 * 数据响应封装类
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/3 20:33
 */
public class ApiResponse {

    public ApiResponse(String body,String message, String code) {
        this.code = code;
        this.message = "\"" + message + "\"";
        this.body = body;
    }

    public ApiResponse(String message, String code) {
        this.code = code;
        this.message = "\"" + message + "\"";
        this.body = "\"\"";
    }

    public static ApiResponse getSuccess(String body){
        return new ApiResponse(body,Constants.API_MESSAGE_SUCCESS, ResponseCode.API_CODE_CALL_SUCCESS);
    }

    public static ApiResponse getSuccess(){
        return new ApiResponse(Constants.API_MESSAGE_SUCCESS, ResponseCode.API_CODE_CALL_SUCCESS);
    }

    @Deprecated
    public ApiResponse(HttpServletRequest request, String body, String message, String code, Object... msgParam) {
        super();
        this.body = body;
        this.code = code;
        try {
            this.message = "\"" + MessageResolver.getMessage(request, message, msgParam) + "\"";
        } catch (Exception e) {
            // TODO: handle exception
            this.message = "\"" + message + "\"";
        }
    }

    /**
     * 返回信息主体
     */
    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    /**
     * API调用提示信息
     */
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * API接口调用状态
     */
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "{\"body\":" + body + ", \"message\":" + message + ", \"code\":" + code + "}";
    }

    public String sourceToString() {
        return "{\"source\":" + body + ", \"message\":" + message + ", \"code\":" + code + "}";
    }

    private String body;
    private String message;
    private String status;
    private String code;
}
