package com.bfly.core.enums;

/**
 * 数据类型
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/19 10:43
 */
public enum DataType {

    STRING(1, "字符串文本"), INTEGER(2, "整型文本"), FLOAT(3, "浮点型文本"), TEXTAREA(4, "文本区"),
    DATETIME(5, "日期"), SELECT(6, "下拉列表"), CHECKBOX(7, "复选框"), SINGLEBOX(8, "单选框"),
    FILE(9, "附件"), IMAGE(10, "图片");

    private int id;
    private String name;

    DataType(int id, String name) {
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
    }}
