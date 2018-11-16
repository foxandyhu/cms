package com.jeecms.cms.entity.assist;

import com.jeecms.core.entity.CmsSite;

import java.io.Serializable;
import java.util.Date;


public class CmsSiteAccessCountHour implements Serializable {
    private static final long serialVersionUID = 1L;

    // primary key
    private Integer id;

    // fields
    private Long hourPv;
    private Long hourIp;
    private Long hourUv;
    private Date accessDate;
    private Integer accessHour;

    // many to one
    private CmsSite site;


    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }


    public Long getHourPv() {
        return hourPv;
    }

    public void setHourPv(Long hourPv) {
        this.hourPv = hourPv;
    }

    public Long getHourIp() {
        return hourIp;
    }

    public void setHourIp(Long hourIp) {
        this.hourIp = hourIp;
    }


    public Long getHourUv() {
        return hourUv;
    }

    public void setHourUv(Long hourUv) {
        this.hourUv = hourUv;
    }

    public Date getAccessDate() {
        return accessDate;
    }


    public void setAccessDate(Date accessDate) {
        this.accessDate = accessDate;
    }

    public Integer getAccessHour() {
        return accessHour;
    }

    public void setAccessHour(Integer accessHour) {
        this.accessHour = accessHour;
    }

    public CmsSite getSite() {
        return site;
    }


    public void setSite(CmsSite site) {
        this.site = site;
    }
}