package com.bfly.cms.logs.entity;

import com.bfly.cms.siteconfig.entity.CmsSite;
import com.bfly.common.util.DateUtils;
import com.bfly.cms.user.entity.CmsUser;
import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;

import javax.persistence.*;
import java.io.Serializable;

/**
 * CMS日志操作
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/15 16:40
 */
@Entity
@Table(name = "jc_log")
public class CmsLog implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final int LOGIN_SUCCESS = 1;
    public static final int LOGIN_FAILURE = 2;
    public static final int OPERATING = 3;

    public static final String LOGIN_SUCCESS_TITLE = "login success";
    public static final String LOGIN_FAILURE_TITLE = "login failure";

    @Id
    @Column(name = "log_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 日志类型
     */
    @Column(name = "category")
    private Integer category;

    /**
     * 日志时间
     */
    @Column(name = "log_time")
    private java.util.Date time;

    /**
     * IP地址
     */
    @Column(name = "ip")
    private String ip;

    /**
     * URL地址
     */
    @Column(name = "url")
    private String url;
    /**
     * 日志标题
     */
    @Column(name = "title")
    private String title;
    /**
     * 日志内容
     */
    @Column(name = "content")
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private CmsUser user;

    @ManyToOne
    @JoinColumn(name = "site_id")
    private CmsSite site;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCategory() {
        return category;
    }


    public void setCategory(Integer category) {
        this.category = category;
    }


    public java.util.Date getTime() {
        return time;
    }


    public void setTime(java.util.Date time) {
        this.time = time;
    }


    public String getIp() {
        return ip;
    }


    public void setIp(String ip) {
        this.ip = ip;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public String getTitle() {
        return title;
    }


    public void setTitle(String title) {
        this.title = title;
    }


    public String getContent() {
        return content;
    }


    public void setContent(String content) {
        this.content = content;
    }


    public CmsUser getUser() {
        return user;
    }


    public void setUser(CmsUser user) {
        this.user = user;
    }


    public CmsSite getSite() {
        return site;
    }


    public void setSite(CmsSite site) {
        this.site = site;
    }


    public JSONObject convertToJson() {
        JSONObject json = new JSONObject();
        if (getId() != null) {
            json.put("id", getId());
        } else {
            json.put("id", "");
        }
        if (getCategory() != null) {
            json.put("category", getCategory());
        } else {
            json.put("category", "");
        }
        if (getTime() != null) {
            json.put("time", DateUtils.parseDateToDateStr(getTime()));
        } else {
            json.put("time", "");
        }
        if (StringUtils.isNotBlank(getIp())) {
            json.put("ip", getIp());
        } else {
            json.put("ip", "");
        }
        if (StringUtils.isNotBlank(getUrl())) {
            json.put("url", getUrl());
        } else {
            json.put("url", "");
        }
        if (StringUtils.isNotBlank(getTitle())) {
            json.put("title", getTitle());
        } else {
            json.put("title", "");
        }
        if (StringUtils.isNotBlank(getContent())) {
            json.put("content", getContent());
        } else {
            json.put("content", "");
        }
        if (getUser() != null && getUser().getId() != null) {
            json.put("userId", getUser().getId());
        } else {
            json.put("userId", "");
        }
        if (getUser() != null && StringUtils.isNotBlank(getUser().getUsername())) {
            json.put("userName", getUser().getUsername());
        } else {
            json.put("userName", "");
        }
        return json;
    }
}