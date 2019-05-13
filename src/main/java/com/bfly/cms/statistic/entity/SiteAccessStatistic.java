package com.bfly.cms.statistic.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 访问统计
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/17 10:08
 */
@Entity
@Table(name = "site_access_statistic")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class SiteAccessStatistic implements Serializable {
    public static final String STATISTIC_ALL = "ALL";
    public static final String STATISTIC_SOURCE = "source";
    public static final String STATISTIC_ENGINE = "engine";
    public static final String STATISTIC_LINK = "link";
    public static final String STATISTIC_KEYWORD = "keyword";
    public static final String STATISTIC_AREA = "area";
    public static final long serialVersionUID = 1L;
    public static final int STATISTIC_TARGET_PV = 0;
    public static final int STATISTIC_TARGET_IP = 1;
    public static final int STATISTIC_TARGET_VISITORS = 2;
    public static final int STATISTIC_TARGET_VISITSECOND = 3;
    public static final int STATISTIC_TARGET_ALL = 4;

    @Id
    @Column(name = "int")
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
    private int pv;

    /**
     * ip量
     */
    @Column(name = "ip")
    private int ip;

    /**
     * 访客数量
     */
    @Column(name = "visitors")
    private int visitors;

    /**
     * 人均浏览次数
     */
    @Column(name = "pages_aver")
    private int pagesAver;

    /**
     * 人均访问时长（秒）
     */
    @Column(name = "visit_second_aver")
    private int visitSecondAver;

    /**
     * 统计分类（all代表当天所有访问量的统计）
     */
    @Column(name = "statisitc_type")
    private String statisitcType;

    /**
     * 统计列值
     */
    @Column(name = "statistic_column_value")
    private String statisticColumnValue;

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

    public int getPv() {
        return pv;
    }

    public void setPv(int pv) {
        this.pv = pv;
    }

    public int getIp() {
        return ip;
    }

    public void setIp(int ip) {
        this.ip = ip;
    }

    public int getVisitors() {
        return visitors;
    }

    public void setVisitors(int visitors) {
        this.visitors = visitors;
    }

    public int getPagesAver() {
        return pagesAver;
    }

    public void setPagesAver(int pagesAver) {
        this.pagesAver = pagesAver;
    }

    public int getVisitSecondAver() {
        return visitSecondAver;
    }

    public void setVisitSecondAver(int visitSecondAver) {
        this.visitSecondAver = visitSecondAver;
    }

    public String getStatisitcType() {
        return statisitcType;
    }

    public void setStatisitcType(String statisitcType) {
        this.statisitcType = statisitcType;
    }

    public String getStatisticColumnValue() {
        return statisticColumnValue;
    }

    public void setStatisticColumnValue(String statisticColumnValue) {
        this.statisticColumnValue = statisticColumnValue;
    }
}