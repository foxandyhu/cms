package com.bfly.core.enums;

/**
 * 水印位置枚举
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/20 9:33
 */
public enum WaterMarkPos {

    RANDOM(0, "随机"), TOP_LEFT(1, "左上"), TOP_RIGHT(2, "右上"),
    BOTTOM_LEFT(3, "左下"), BOTTOM_RIGHT(4, "右下"), MIDDLE(5, "居中");

    private int id;
    private String name;

    WaterMarkPos(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static WaterMarkPos getPos(int id) {
        for (WaterMarkPos pos : WaterMarkPos.values()) {
            if (pos.getId() == id) {
                return pos;
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
