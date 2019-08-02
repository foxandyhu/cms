package com.bfly.core.enums;

/**
 * 留言状态
 *
 * @author andy_hulibo@163.com
 * @date 2019/8/1 11:16
 */
public enum GuestBookStatus {

    WAIT_CHECK(0, "待审核"),
    UNPASSED(1, "审核不通过"), PASSED(2, "审核通过");

    private int id;
    private String name;

    GuestBookStatus(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * 获得留言状态
     * @author andy_hulibo@163.com
     * @date 2019/8/1 11:17
     */
    public static GuestBookStatus getStatus(int id) {
        for (GuestBookStatus status : GuestBookStatus.values()) {
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
