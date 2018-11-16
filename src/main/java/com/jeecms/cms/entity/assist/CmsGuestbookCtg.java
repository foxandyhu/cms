package com.jeecms.cms.entity.assist;

import com.jeecms.core.entity.CmsSite;
import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;


public class CmsGuestbookCtg implements Serializable {
    private static final long serialVersionUID = 1L;

    public JSONObject convertToJson()
            throws JSONException {
        JSONObject json = new JSONObject();
        if (getId() != null) {
            json.put("id", getId());
        } else {
            json.put("id", "");
        }
        if (StringUtils.isNotBlank(getDescription())) {
            json.put("description", getDescription());
        } else {
            json.put("description", "");
        }
        if (StringUtils.isNotBlank(getName())) {
            json.put("name", getName());
        } else {
            json.put("name", "");
        }
        if (getPriority() != null) {
            json.put("priority", getPriority());
        } else {
            json.put("priority", "");
        }
        return json;
    }

    public void init() {
        if (getPriority() == null) {
            setPriority(10);
        }
    }

    // primary key
    private Integer id;

    // fields
    private String name;
    private Integer priority;
    private String description;

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


    public Integer getPriority() {
        return priority;
    }


    public void setPriority(Integer priority) {
        this.priority = priority;
    }


    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }


    public CmsSite getSite() {
        return site;
    }


    public void setSite(CmsSite site) {
        this.site = site;
    }

}