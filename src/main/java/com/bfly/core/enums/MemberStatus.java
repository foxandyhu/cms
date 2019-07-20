package com.bfly.core.enums;

/**
 * 会员状态
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/19 12:53
 */
public enum MemberStatus {

    UNCHECK(0, "待审核"), AVAILABLE(1, "正常"), DISABLE(2, "已禁用");

    private int id;
    private String name;

    MemberStatus(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * 获得短信类型
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/17 12:49
     */
    public static MemberStatus getStatus(int id) {
        for (MemberStatus type : MemberStatus.values()) {
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
