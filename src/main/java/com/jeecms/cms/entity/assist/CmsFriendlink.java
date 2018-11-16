package com.jeecms.cms.entity.assist;

import com.jeecms.core.entity.CmsSite;
import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class CmsFriendlink implements Serializable {
    private static final long serialVersionUID = 1L;

    public void init() {
        if (getPriority() == null) {
            setPriority(10);
        }
        if (getViews() == null) {
            setViews(0);
        }
        if (getEnabled() == null) {
            setEnabled(true);
        }
        blankToNull();
    }

    public void blankToNull() {
        if (StringUtils.isBlank(getLogo())) {
            setLogo(null);
        }
    }

    public JSONObject convertToJson()
            throws JSONException {
        JSONObject json = new JSONObject();
        if (getId() != null) {
            json.put("id", getId());
        } else {
            json.put("id", "");
        }
        if (getPriority() != null) {
            json.put("priority", getPriority());
        } else {
            json.put("priority", "");
        }
        if (StringUtils.isNotBlank(getName())) {
            json.put("name", getName());
        } else {
            json.put("name", "");
        }
        if (StringUtils.isNotBlank(getDomain())) {
            json.put("domain", getDomain());
        } else {
            json.put("domain", "");
        }
        if (StringUtils.isNotBlank(getLogo())) {
            json.put("logo", getLogo());
        } else {
            json.put("logo", "");
        }
        if (StringUtils.isNotBlank(getEmail())) {
            json.put("email", getEmail());
        } else {
            json.put("email", "");
        }
        if (StringUtils.isNotBlank(getDescription())) {
            json.put("description", getDescription());
        } else {
            json.put("description", "");
        }
        if (getViews() != null) {
            json.put("views", getViews());
        } else {
            json.put("views", "");
        }
        if (getEnabled() != null) {
            json.put("enabled", getEnabled());
        } else {
            json.put("enabled", "");
        }
        if (getCategory() != null && StringUtils.isNotBlank(getCategory().getName())) {
            json.put("categoryName", getCategory().getName());
        } else {
            json.put("categoryName", "");
        }
        if (getCategory() != null && getCategory().getId() != null) {
            json.put("categoryId", getCategory().getId());
        } else {
            json.put("categoryId", "");
        }
        return json;
    }

    // primary key
    private Integer id;

    // fields
    private String name;
    private String domain;
    private String logo;
    private String email;
    private String description;
    private Integer views;
    private Integer priority;
    private Boolean enabled;

    // many to one
    private CmsFriendlinkCtg category;
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


    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Boolean getEnabled() {
        return enabled;
    }


    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public CmsFriendlinkCtg getCategory() {
        return category;
    }

    public void setCategory(CmsFriendlinkCtg category) {
        this.category = category;
    }

    public CmsSite getSite() {
        return site;
    }


    public void setSite(CmsSite site) {
        this.site = site;
    }

}