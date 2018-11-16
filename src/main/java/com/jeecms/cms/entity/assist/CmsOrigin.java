package com.jeecms.cms.entity.assist;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;

import java.io.Serializable;


public class CmsOrigin implements Serializable {
    private static final long serialVersionUID = 1L;

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
        if (getRefCount() != null) {
            json.put("refCount", getRefCount());
        } else {
            json.put("refCount", "");
        }
        return json;
    }

    // primary key
    private Integer id;

    // fields
    private String name;
    private Integer refCount;


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

    public Integer getRefCount() {
        return refCount;
    }


    public void setRefCount(Integer refCount) {
        this.refCount = refCount;
    }


}