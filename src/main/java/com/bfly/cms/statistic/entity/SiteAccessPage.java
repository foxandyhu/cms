package com.bfly.cms.statistic.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 每次访问访问页面记录详细信息
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/17 10:06
 */
@Entity
@Table(name = "site_access_pages")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class SiteAccessPage implements Serializable {

    private static final long serialVersionUID = -3382722684352955406L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * 会话ID
     * @author andy_hulibo@163.com
     * @date 2019/7/23 17:16
     */
    @Column(name = "session_id")
    private String sessionId;

    /**
     * 访问页面
     */
    @Column(name = "access_page")
    private String accessPage;

    /**
     * 访问时间
     */
    @Column(name = "access_time")
    private Date accessTime;

    /**
     * 访问日期
     */
    @Column(name = "access_date")
    private Date accessDate;

    /**
     * 停留时长（秒）
     */
    @Column(name = "visit_second")
    private long visitSecond;

    /**
     * 用户访问页面的索引
     */
    @Column(name = "seq")
    private long seq;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccessPage() {
        return accessPage;
    }

    public void setAccessPage(String accessPage) {
        this.accessPage = accessPage;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Date getAccessTime() {
        return accessTime;
    }

    public void setAccessTime(Date accessTime) {
        this.accessTime = accessTime;
    }

    public Date getAccessDate() {
        return accessDate;
    }

    public void setAccessDate(Date accessDate) {
        this.accessDate = accessDate;
    }

    public long getVisitSecond() {
        return visitSecond;
    }

    public void setVisitSecond(long visitSecond) {
        this.visitSecond = visitSecond;
    }

    public long getSeq() {
        return seq;
    }

    public void setSeq(long seq) {
        this.seq = seq;
    }
}