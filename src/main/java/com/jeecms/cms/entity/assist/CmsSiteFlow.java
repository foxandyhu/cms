package com.jeecms.cms.entity.assist;

import com.jeecms.core.entity.CmsSite;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "jc_site_flow")
public class CmsSiteFlow implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "flow_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "access_ip")
    private String accessIp;

    @Column(name = "access_date")
    private String accessDate;

    @Column(name = "access_time")
    private Date accessTime;

    @Column(name = "access_page")
    private String accessPage;

    @Column(name = "referer_website")
    private String refererWebSite;

    @Column(name = "referer_page")
    private String refererPage;

    @Column(name = "referer_keyword")
    private String refererKeyword;

    @Column(name = "area")
    private String area;

    @Column(name = "session_id")
    private String sessionId;

    @ManyToOne
    @JoinColumn(name = "site_id")
    private CmsSite site;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccessIp() {
        return accessIp;
    }

    public void setAccessIp(String accessIp) {
        this.accessIp = accessIp;
    }

    public String getAccessDate() {
        return accessDate;
    }

    public void setAccessDate(String accessDate) {
        this.accessDate = accessDate;
    }

    public Date getAccessTime() {
        return accessTime;
    }

    public void setAccessTime(Date accessTime) {
        this.accessTime = accessTime;
    }

    public String getAccessPage() {
        return accessPage;
    }


    public void setAccessPage(String accessPage) {
        this.accessPage = accessPage;
    }

    public String getRefererWebSite() {
        return refererWebSite;
    }


    public void setRefererWebSite(String refererWebSite) {
        this.refererWebSite = refererWebSite;
    }

    public String getRefererPage() {
        return refererPage;
    }

    public void setRefererPage(String refererPage) {
        this.refererPage = refererPage;
    }

    public String getRefererKeyword() {
        return refererKeyword;
    }

    public void setRefererKeyword(String refererKeyword) {
        this.refererKeyword = refererKeyword;
    }


    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }


    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }


    public CmsSite getSite() {
        return site;
    }

    public void setSite(CmsSite site) {
        this.site = site;
    }
}