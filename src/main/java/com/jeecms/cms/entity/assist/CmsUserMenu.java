package com.jeecms.cms.entity.assist;

import com.jeecms.core.entity.CmsUser;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;


public class CmsUserMenu implements Serializable {
    private static final long serialVersionUID = 1L;

    // primary key
    private Integer id;

    // fields
    private String name;
    private String url;
    private Integer priority;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private CmsUser user;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getUrl() {
        return url;
    }


    public void setUrl(String url) {
        this.url = url;
    }


    public Integer getPriority() {
        return priority;
    }


    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public CmsUser getUser() {
        return user;
    }


    public void setUser(CmsUser user) {
        this.user = user;
    }


}