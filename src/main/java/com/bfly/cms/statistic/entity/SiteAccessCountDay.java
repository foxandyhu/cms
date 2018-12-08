package com.bfly.cms.statistic.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 每日统计页数访问情况
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/17 10:01
 */
@Entity
@Table(name = "site_access_count_day")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class SiteAccessCountDay implements Serializable {

    private static final long serialVersionUID = 4490571738845608734L;

    @Id
    @Column(name = "access_count")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * 访问页数
     */
    @Column(name = "page_count")
    private int pageCount;

    /**
     * 用户数
     */
    @Column(name = "visitors")
    private int visitors;

    /**
     * 统计日期
     */
    @Column(name = "statistic_date")
    private Date statisticDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getVisitors() {
        return visitors;
    }

    public void setVisitors(int visitors) {
        this.visitors = visitors;
    }

    public Date getStatisticDate() {
        return statisticDate;
    }

    public void setStatisticDate(Date statisticDate) {
        this.statisticDate = statisticDate;
    }
}