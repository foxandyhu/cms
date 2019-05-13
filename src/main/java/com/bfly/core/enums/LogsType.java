package com.bfly.core.enums;

/**
 * 日志类型
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/10 16:45
 */
public enum LogsType {

    OP_LOG(1, "操作日志"), LOGIN_LOG(2, "登录日志"), LOGOUT_LOG(3, "登出日志");

    private int id;
    private String type;

    LogsType(int id, String type) {
        this.id = id;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /**
     * 获得日志类型
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/10 16:47
     */
    public static LogsType get(int id) {
        for (LogsType type : LogsType.values()) {
            if (id == type.getId()) {
                return type;
            }
        }
        return null;
    }
}
