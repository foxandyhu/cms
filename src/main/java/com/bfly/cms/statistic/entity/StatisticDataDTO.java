package com.bfly.cms.statistic.entity;

import java.io.Serializable;

/**
 * 统计数据对象
 *
 * @author andy_hulibo@163.com
 * @date 2019/7/24 16:51
 */
public class StatisticDataDTO implements Serializable {
    private static final long serialVersionUID = -1132132857954249521L;

    private long ip;
    private long uv;
    private long pv;
    private String time;
    private String value;

    public long getIp() {
        return ip;
    }

    public void setIp(long ip) {
        this.ip = ip;
    }

    public long getUv() {
        return uv;
    }

    public void setUv(long uv) {
        this.uv = uv;
    }

    public long getPv() {
        return pv;
    }

    public void setPv(long pv) {
        this.pv = pv;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
