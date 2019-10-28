package com.bfly.core.enums;

/**
 * 问卷调查类型
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/19 12:53
 */
public enum VoteType {

    SINGLE(1, "单选题"), MULTI(2, "多选题"), TEXT(3, "问答题");

    private int id;
    private String name;

    VoteType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * 获得类型
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/17 12:47
     */
    public static VoteType getType(int id) {
        for (VoteType type : VoteType.values()) {
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
