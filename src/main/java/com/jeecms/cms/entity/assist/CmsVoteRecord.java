package com.jeecms.cms.entity.assist;

import com.jeecms.core.entity.CmsUser;

import java.io.Serializable;
import java.util.Date;


public class CmsVoteRecord implements Serializable {
    private static final long serialVersionUID = 1L;

    // primary key
    private Integer id;

    // fields
    private Date time;
    private String ip;
    private String cookie;

    // many to one
    private CmsUser user;
    private CmsVoteTopic topic;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getIp() {
        return ip;
    }


    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }


    public CmsUser getUser() {
        return user;
    }


    public void setUser(CmsUser user) {
        this.user = user;
    }


    public CmsVoteTopic getTopic() {
        return topic;
    }

    public void setTopic(CmsVoteTopic topic) {
        this.topic = topic;
    }


}