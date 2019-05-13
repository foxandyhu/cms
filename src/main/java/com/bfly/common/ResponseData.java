package com.bfly.common;

import com.bfly.common.json.JsonUtil;
import com.bfly.core.enums.SysError;

import java.io.Serializable;

/**
 * HTTP请求数据返回封装类
 *
 * @author andy_hulibo
 * 2017年4月17日下午6:11:13
 */
public class ResponseData implements Serializable {

    private static final long serialVersionUID = -6507444341663513311L;

    /**
     * 错误码
     */
    private String errorCode;

    /**
     * 消息实体
     */
    private Object message;

    /**
     * 处理状态 1成功 -1失败
     */
    private int status;

    /**
     * 请求成功，返回未加密的JSON数据
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/6 16:52
     */
    public static String getSuccess(Object msg) {
        return JsonUtil.toJsonStringFilterPropter(new ResponseData(null, msg), "").toJSONString();
    }

    /**
     * 请求失败，返回未加密的JSON数据
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/6 16:50
     */
    public static String getFail(Object msg) {
        return getFail(msg);
    }

    /**
     * 请求失败，返回未加密的JSON数据
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/6 16:50
     */
    public static String getFail(SysError error, Object msg) {
        error = error == null ? SysError.ERROR : error;
        msg = msg == null ? error.getMessage() : msg;
        return JsonUtil.toJsonStringFilterPropter(new ResponseData(error.getCode(), msg), "").toJSONString();
    }

    public ResponseData(String code, Object msg) {
        this.errorCode = code;
        this.message = msg;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
