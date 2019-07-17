package com.bfly.core.enums;

/**
 * 短信状态
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/19 12:53
 */
public enum SmsStatus {

    UNKNOW(0, "未知"),
    PENDING(1, "待发送"), SUCCESS(2, "发送成功"), FAIL(3, "发送失败");

    private int id;
    private String name;

    SmsStatus(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * 获得状态
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/17 12:47
     */
    public static SmsStatus getStatus(int id) {
        for (SmsStatus status : SmsStatus.values()) {
            if (status.getId() == id) {
                return status;
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
