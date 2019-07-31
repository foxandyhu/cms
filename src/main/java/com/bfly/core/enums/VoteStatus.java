package com.bfly.core.enums;

/**
 * 问卷调查状态
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/19 12:53
 */
public enum VoteStatus {

    NO_START(1, "未开始"), PROCESSING(2, "进行中"), FINISHED(3, "已结束");

    private int id;
    private String name;

    VoteStatus(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * 获得状态
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/17 12:47
     */
    public static VoteStatus getStatus(int id) {
        for (VoteStatus status : VoteStatus.values()) {
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
