package com.jeecms.cms.entity.main;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.json.JSONObject;

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
@Table(name = "jc_api_info")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class ApiInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

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
    private Integer limitCallDay;

    /**
     * 总调用次数
     */
    @Column(name = "call_total_count")
    private Integer callTotalCount;

    /**
     * 月调用次数
     */
    @Column(name = "call_month_count")
    private Integer callMonthCount;

    /**
     * 周调用次数
     */
    @Column(name = "call_week_count")
    private Integer callWeekCount;

    /**
     * 日调用次数
     */
    @Column(name = "call_day_count")
    private Integer callDayCount;

    /**
     * 最后一次调用时间
     */
    @Column(name = "last_call_time")
    private Date lastCallTime;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "apiInfo")
    private Set<ApiRecord> callRecords;


    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
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

    public Integer getLimitCallDay() {
        return limitCallDay;
    }

    public void setLimitCallDay(Integer limitCallDay) {
        this.limitCallDay = limitCallDay;
    }


    public Integer getCallTotalCount() {
        return callTotalCount;
    }

    public void setCallTotalCount(Integer callTotalCount) {
        this.callTotalCount = callTotalCount;
    }


    public Integer getCallMonthCount() {
        return callMonthCount;
    }


    public void setCallMonthCount(Integer callMonthCount) {
        this.callMonthCount = callMonthCount;
    }

    public Integer getCallWeekCount() {
        return callWeekCount;
    }

    public void setCallWeekCount(Integer callWeekCount) {
        this.callWeekCount = callWeekCount;
    }

    public Integer getCallDayCount() {
        return callDayCount;
    }


    public void setCallDayCount(Integer callDayCount) {
        this.callDayCount = callDayCount;
    }

    public Date getLastCallTime() {
        return lastCallTime;
    }

    public void setLastCallTime(Date lastCallTime) {
        this.lastCallTime = lastCallTime;
    }


    public Set<ApiRecord> getCallRecords() {
        return callRecords;
    }


    public void setCallRecords(Set<ApiRecord> callRecords) {
        this.callRecords = callRecords;
    }

    public JSONObject convertToJson() {
        JSONObject json = new JSONObject();
        if (getId() != null) {
            json.put("id", getId());
        } else {
            json.put("id", "");
        }
        if (StringUtils.isNotBlank(getName())) {
            json.put("name", getName());
        } else {
            json.put("name", "");
        }
        if (StringUtils.isNotBlank(getUrl())) {
            json.put("url", getUrl());
        } else {
            json.put("url", "");
        }
        if (StringUtils.isNotBlank(getCode())) {
            json.put("code", getCode());
        } else {
            json.put("code", "");
        }
        if (getLimitCallDay() != null) {
            json.put("limitCallDay", getLimitCallDay());
        } else {
            json.put("limitCallDay", "");
        }
        if (getCallTotalCount() != null) {
            json.put("callTotalCount", getCallTotalCount());
        } else {
            json.put("callTotalCount", "");
        }
        if (getCallMonthCount() != null) {
            json.put("callMonthCount", getCallMonthCount());
        } else {
            json.put("callMonthCount", "");
        }
        if (getCallWeekCount() != null) {
            json.put("callWeekCount", getCallWeekCount());
        } else {
            json.put("callWeekCount", "");
        }
        if (getCallDayCount() != null) {
            json.put("callDayCount", getCallDayCount());
        } else {
            json.put("callDayCount", "");
        }
        json.put("disabled", getDisabled());
        return json;
    }

    public void init() {
        if (getCallDayCount() == null) {
            setCallDayCount(0);
        }
        if (getCallMonthCount() == null) {
            setCallMonthCount(0);
        }
        if (getCallWeekCount() == null) {
            setCallWeekCount(0);
        }
        if (getCallTotalCount() == null) {
            setCallTotalCount(0);
        }
        if (getLimitCallDay() == null) {
            setLimitCallDay(0);
        }
    }

    public boolean getDisabled() {
        return isDisabled();
    }
}