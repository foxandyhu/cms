package com.jeecms.cms.entity.assist;

import com.jeecms.core.entity.CmsSite;
import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;

import java.io.Serializable;

public class CmsKeyword implements Serializable {
    private static final long serialVersionUID = 1L;

    public void init() {
        if (getDisabled() == null) {
            setDisabled(false);
        }
    }

    public JSONObject convertToJson() {
        JSONObject json = new JSONObject();
        if (getId() != null) {
            json.put("id", getId());
        } else {
            json.put("id", "");
        }
        if (StringUtils.isNotBlank(getName())) {
            json.put("name", getName());
        } else {
            json.put("name", "");
        }
        if (StringUtils.isNotBlank(getUrl())) {
            json.put("url", getUrl());
        } else {
            json.put("url", "");
        }
        if (getDisabled() != null) {
            json.put("disabled", getDisabled());
        } else {
            json.put("disabled", "");
        }
        return json;
    }

    // primary key
    private Integer id;

    // fields
    private String name;
    private String url;
    private Boolean disabled;

    // many to one
    private CmsSite site;

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

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public CmsSite getSite() {
        return site;
    }


    public void setSite(CmsSite site) {
        this.site = site;
    }


}