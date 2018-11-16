package com.jeecms.cms.entity.assist;

import com.jeecms.core.entity.CmsSite;
import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;


public class CmsAdvertisingSpace implements Serializable {
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
        if (getEnabled() != null) {
            json.put("enabled", getEnabled());
        } else {
            json.put("enabled", "");
        }
        return json;
    }

    public void init() {
        if (getEnabled() == null) {
            setEnabled(true);
        }
    }

    // primary key
    private Integer id;

    // fields
    private String name;
    private String description;
    private Boolean enabled;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }


    public CmsSite getSite() {
        return site;
    }

    public void setSite(CmsSite site) {
        this.site = site;
    }


}