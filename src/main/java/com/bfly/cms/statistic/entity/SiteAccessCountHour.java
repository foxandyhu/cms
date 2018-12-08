package com.bfly.cms.statistic.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 站点访问小时数据统计
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/27 9:59
 */
@Entity
@Table(name = "site_access_count_hour")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class SiteAccessCountHour implements Serializable {

    private static final long serialVersionUID = 3236085379679188622L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * 小时PV
     */
    @Column(name = "hour_pv")
    private int hourPv;

    /**
     * 小时IP
     */
    @Column(name = "hour_ip")
    private int hourIp;

    /**
     * 小时访客数
     */
    @Column(name = "hour_uv")
    private int hourUv;

    @Column(name = "access_date")
    private Date accessDate;

    @Column(name = "access_hour")
    private int accessHour;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHourPv() {
        return hourPv;
    }

    public void setHourPv(int hourPv) {
        this.hourPv = hourPv;
    }

    public int getHourIp() {
        return hourIp;
    }

    public void setHourIp(int hourIp) {
        this.hourIp = hourIp;
    }

    public int getHourUv() {
        return hourUv;
    }

    public void setHourUv(int hourUv) {
        this.hourUv = hourUv;
    }

    public Date getAccessDate() {
        return accessDate;
    }

    public void setAccessDate(Date accessDate) {
        this.accessDate = accessDate;
    }

    public int getAccessHour() {
        return accessHour;
    }

    public void setAccessHour(int accessHour) {
        this.accessHour = accessHour;
    }
}