package com.bfly.core.enums;

/**
 * 数据类型
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/19 10:43
 */
public enum DataType {

    /**
     * 字符串文本
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/3 16:38
     */
    STRING(1, "字符串文本"),

    /**
     * 文本区
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/3 16:38
     */
    TEXTAREA(2, "文本域"),


    /**
     * 日期
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/3 16:39
     */
    DATETIME(3, "日期"),


    /**
     * 下拉列表
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/3 16:39
     */
    SELECT(4, "下拉列表"),

    /**
     * 多选框
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/3 16:39
     */
    CHECKBOX(5, "多选框"),

    /**
     * 单选框
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/3 16:39
     */
    SINGLEBOX(6, "单选框"),

    /**
     * 附件集
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/3 16:39
     */
    ATTACHMENTS(7, "附件集"),

    /**
     * 图片集
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/3 16:39
     */
    PICTURES(8, "图片集"),

    /**
     * 富文本
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/9 20:26
     */
    EDITOR(9, "富文本");

    private int id;
    private String name;


    public static DataType getType(int type) {
        for (DataType dataType : DataType.values()) {
            if (type == dataType.getId()) {
                return dataType;
            }
        }
        return null;
    }

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
    }
}
