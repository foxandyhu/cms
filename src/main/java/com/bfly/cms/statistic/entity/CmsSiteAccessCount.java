package com.bfly.cms.statistic.entity;

import com.bfly.cms.siteconfig.entity.CmsSite;
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
@Table(name = "jc_site_access_count")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class CmsSiteAccessCount implements Serializable {
    private static final long serialVersionUID = 1L;


    @Id
    @Column(name = "access_count")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 访问页数
     */
    @Column(name = "page_count")
    private Integer pageCount;

    /**
     * 用户数
     */
    @Column(name = "visitors")
    private Integer visitors;

    /**
     * 统计日期
     */
    @Column(name = "statistic_date")
    private Date statisticDate;

    @ManyToOne
    @JoinColumn(name = "site_id")
    private CmsSite site;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public Integer getVisitors() {
        return visitors;
    }

    public void setVisitors(Integer visitors) {
        this.visitors = visitors;
    }


    public Date getStatisticDate() {
        return statisticDate;
    }

    public void setStatisticDate(Date statisticDate) {
        this.statisticDate = statisticDate;
    }


    public CmsSite getSite() {
        return site;
    }


    public void setSite(CmsSite site) {
        this.site = site;
    }


}