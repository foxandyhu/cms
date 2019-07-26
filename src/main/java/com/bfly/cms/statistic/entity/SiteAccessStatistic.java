package com.bfly.cms.statistic.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 访问统计汇总表
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/17 10:08
 */
@Entity
@Table(name = "site_access_statistic")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class SiteAccessStatistic implements Serializable {

    public static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * 统计日期
     */
    @Column(name = "statistic_date")
    private Date statisticDate;

    /**
     * pv量
     */
    @Column(name = "pv")
    private long pv;

    /**
     * ip量
     */
    @Column(name = "ip")
    private long ip;

    /**
     * 访客数量
     */
    @Column(name = "visitors")
    private long visitors;

    /**
     * 人均浏览次数
     */
    @Column(name = "pages_avg")
    private long pagesAvg;

    /**
     * 人均访问时长（秒）
     */
    @Column(name = "visit_second_avg")
    private long visitSecondAvg;

    /**
     * 统计分类（all代表当天所有访问量的统计）
     */
    @Column(name = "statisitc_key")
    private String statisitcKey;

    /**
     * 统计列值
     */
    @Column(name = "statistic_value")
    private String statisticValue;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getStatisticDate() {
        return statisticDate;
    }

    public void setStatisticDate(Date statisticDate) {
        this.statisticDate = statisticDate;
    }

    public long getPv() {
        return pv;
    }

    public void setPv(long pv) {
        this.pv = pv;
    }

    public long getIp() {
        return ip;
    }

    public void setIp(long ip) {
        this.ip = ip;
    }

    public long getVisitors() {
        return visitors;
    }

    public void setVisitors(long visitors) {
        this.visitors = visitors;
    }

    public long getPagesAvg() {
        return pagesAvg;
    }

    public void setPagesAvg(long pagesAvg) {
        this.pagesAvg = pagesAvg;
    }

    public long getVisitSecondAvg() {
        return visitSecondAvg;
    }

    public void setVisitSecondAvg(long visitSecondAvg) {
        this.visitSecondAvg = visitSecondAvg;
    }

    public String getStatisitcKey() {
        return statisitcKey;
    }

    public void setStatisitcKey(String statisitcKey) {
        this.statisitcKey = statisitcKey;
    }

    public String getStatisticValue() {
        return statisticValue;
    }

    public void setStatisticValue(String statisticValue) {
        this.statisticValue = statisticValue;
    }
}