package com.bfly.core.enums;

/**
 * 系统错误枚举
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/7 16:52
 */
public enum SysError {

    ERROR("500", "系统异常"),
    PARAM_ERROR("101", "参数错误!"),
    DATA_REPEAT("102","数据重复!");
    private String code;
    private String message;

    SysError(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 根据code获得对应的枚举
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/7 17:10
     */
    public static SysError get(String code) {
        for (SysError error : SysError.values()) {
            if (error.getCode().equalsIgnoreCase(code)) {
                return error;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
