package com.bfly.core.enums;

/**
 * 短信状态
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/19 12:53
 */
public enum SmsStatus {

    DRAFT(0, "草稿"), PENDING(1, "待发送"), SUCCESS(2, "发送成功"), FAIL(4, "发送失败");

    private int id;
    private String name;

    SmsStatus(int id, String name) {
        this.id = id;
        this.name = name;
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
