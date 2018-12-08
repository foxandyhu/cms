package com.bfly.cms.webservice.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * API接口信息
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/16 9:54
 */
@Entity
@Table(name = "ws_info")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class WsInfo implements Serializable {

    private static final long serialVersionUID = 5543204016693148803L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * 接口名称
     */
    @Column(name = "api_name")
    private String name;

    /**
     * 接口URL
     */
    @Column(name = "api_url")
    private String url;

    /**
     * 接口代码
     */
    @Column(name = "api_code")
    private String code;

    /**
     * 是否禁用
     */
    @Column(name = "disabled")
    private boolean disabled;

    /**
     * 每日限制调用次数(0无限制)
     */
    @Column(name = "limit_call_day")
    private int limitCallDay;

    /**
     * 总调用次数
     */
    @Column(name = "call_total_count")
    private int callTotalCount;

    /**
     * 月调用次数
     */
    @Column(name = "call_month_count")
    private int callMonthCount;

    /**
     * 周调用次数
     */
    @Column(name = "call_week_count")
    private int callWeekCount;

    /**
     * 日调用次数
     */
    @Column(name = "call_day_count")
    private int callDayCount;

    /**
     * 最后一次调用时间
     */
    @Column(name = "last_call_time")
    private Date lastCallTime;

    /**
     * 调用记录
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "apiInfo")
    private Set<WsRecord> callRecords;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public int getLimitCallDay() {
        return limitCallDay;
    }

    public void setLimitCallDay(int limitCallDay) {
        this.limitCallDay = limitCallDay;
    }

    public int getCallTotalCount() {
        return callTotalCount;
    }

    public void setCallTotalCount(int callTotalCount) {
        this.callTotalCount = callTotalCount;
    }

    public int getCallMonthCount() {
        return callMonthCount;
    }

    public void setCallMonthCount(int callMonthCount) {
        this.callMonthCount = callMonthCount;
    }

    public int getCallWeekCount() {
        return callWeekCount;
    }

    public void setCallWeekCount(int callWeekCount) {
        this.callWeekCount = callWeekCount;
    }

    public int getCallDayCount() {
        return callDayCount;
    }

    public void setCallDayCount(int callDayCount) {
        this.callDayCount = callDayCount;
    }

    public Date getLastCallTime() {
        return lastCallTime;
    }

    public void setLastCallTime(Date lastCallTime) {
        this.lastCallTime = lastCallTime;
    }

    public Set<WsRecord> getCallRecords() {
        return callRecords;
    }

    public void setCallRecords(Set<WsRecord> callRecords) {
        this.callRecords = callRecords;
    }
}