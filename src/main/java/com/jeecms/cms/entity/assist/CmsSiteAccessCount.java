package com.jeecms.cms.entity.assist;

import com.jeecms.core.entity.CmsSite;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Date;


public class CmsSiteAccessCount implements Serializable {
    private static final long serialVersionUID = 1L;


    // primary key
    private Integer id;

    // fields
    private Integer pageCount;
    private Integer visitors;
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