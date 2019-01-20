package com.bfly.core.enums;

/**
 * 云存储类型
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/18 11:40
 */
public enum OssType {

    TENCENT(1, "腾讯云"), ALIBABA(2, "阿里云"), QINIU(3, "七牛云");

    private String name;
    private int id;

    OssType(int id, String name) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }}
