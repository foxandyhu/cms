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
     * 整型文本
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/3 16:38
     */
    INTEGER(2, "整型文本"),

    /**
     * 浮点型文本
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/3 16:38
     */
    FLOAT(3, "浮点型文本"),

    /**
     * 文本区
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/3 16:38
     */
    TEXTAREA(4, "文本域"),


    /**
     * 日期
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/3 16:39
     */
    DATETIME(5, "日期"),


    /**
     * 下拉列表
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/3 16:39
     */
    SELECT(6, "下拉列表"),

    /**
     * 复选框
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/3 16:39
     */
    CHECKBOX(7, "复选框"),

    /**
     * 单选框
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/3 16:39
     */
    SINGLEBOX(8, "单选框"),

    /**
     * 附件
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/3 16:39
     */
    ATTACHMENTS(9, "附件"),

    /**
     * 图片
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/3 16:39
     */
    PICTURES(10, "图片"),

    /**
     * 多媒体
     * @author andy_hulibo@163.com
     * @date 2019/8/3 19:41
     */
    MEDIAS(11,"多媒体");

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
