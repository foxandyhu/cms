package com.bfly.core.enums;

import com.alibaba.fastjson.JSONObject;

/**
 * 内容类型
 *
 * @author andy_hulibo@163.com
 * @date 2019/8/3 14:25
 */
public enum ContentType {

    /**
     * 普通
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/3 14:30
     */
    NORMAL(1, "普通"),

    /**
     * 图文
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/3 14:30
     */
    PICTURE(2, "图文"),

    /**
     * 焦点
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/3 14:30
     */
    FOCUS(3, "焦点"),

    /**
     * 头条
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/3 14:30
     */
    HEADLINE(4, "头条");

    private int id;
    private String name;

    public static ContentType getType(int type) {
        for (ContentType contentType : ContentType.values()) {
            if (type == contentType.getId()) {
                return contentType;
            }
        }
        return null;
    }


    ContentType(int id, String name) {
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
