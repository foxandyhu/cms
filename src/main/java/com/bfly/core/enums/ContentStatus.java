package com.bfly.core.enums;

/**
 * 内容状态
 *
 * @author andy_hulibo@163.com
 * @date 2019/8/1 13:46
 */
public enum ContentStatus {

    DRAFT(0, "草稿"),
    WAIT_CHECK(1, "待审核"), PASSED(2, "审核通过"),
    UNPASSED(3, "审核未通过");

    private int id;
    private String name;

    ContentStatus(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * 获得评论状态
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/1 11:17
     */
    public static ContentStatus getStatus(int id) {
        for (ContentStatus status : ContentStatus.values()) {
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
