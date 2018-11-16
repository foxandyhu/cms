package com.jeecms.cms.entity.assist;

import com.jeecms.core.entity.CmsSite;

import java.io.Serializable;


public class CmsSiteFlow implements Serializable {
    private static final long serialVersionUID = 1L;

    // primary key
    private Integer id;

    // fields
    private String accessIp;
    private String accessDate;
    private java.util.Date accessTime;
    private String accessPage;
    private String refererWebSite;
    private String refererPage;
    private String refererKeyword;
    private String area;
    private String sessionId;

    // many to one
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

    public java.util.Date getAccessTime() {
        return accessTime;
    }

    public void setAccessTime(java.util.Date accessTime) {
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