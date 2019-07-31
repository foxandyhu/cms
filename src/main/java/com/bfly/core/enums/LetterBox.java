package com.bfly.core.enums;

/**
 * 站内信信箱类型
 *
 * @author andy_hulibo@163.com
 * @date 2019/7/31 16:16
 */
public enum LetterBox {

    RECEIVE(1, "收件箱"), SEND(2, "发件箱"), DRAFT(3, "草稿箱"), WASTE(4, "垃圾箱");

    private int id;
    private String name;

    LetterBox(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static LetterBox get(int id) {
        for (LetterBox type : LetterBox.values()) {
            if (id == type.getId()) {
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
