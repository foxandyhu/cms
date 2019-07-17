package com.bfly.core.enums;

/**
 * 短信类型
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/19 12:53
 */
public enum SmsType {

    UNKNOWN(0, "未知"), REGISTERED(1, "注册验证"), RETRIEVE_PASSWORD(2, "找回密码验证");

    private int id;
    private String name;

    SmsType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * 获得短信类型
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/17 12:49
     */
    public static SmsType getType(int id) {
        for (SmsType type : SmsType.values()) {
            if (type.getId() == id) {
                return type;
            }
        }
        return null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
