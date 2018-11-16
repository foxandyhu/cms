package com.jeecms.cms.entity.assist;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Set;


public class CmsWebserviceAuth implements Serializable {
    private static final long serialVersionUID = 1L;

    public JSONObject convertToJson() {
        JSONObject json = new JSONObject();
        if (getId() != null) {
            json.put("id", getId());
        } else {
            json.put("id", "");
        }
        if (StringUtils.isNotBlank(getUsername())) {
            json.put("username", getUsername());
        } else {
            json.put("username", "");
        }
        if (StringUtils.isNotBlank(getPassword())) {
            json.put("password", getPassword());
        } else {
            json.put("password", "");
        }
        if (StringUtils.isNotBlank(getSystem())) {
            json.put("system", getSystem());
        } else {
            json.put("system", "");
        }
        json.put("enable", getEnable());
        return json;
    }

    public boolean getEnable() {
        return isEnable();
    }

    // primary key
    private Integer id;

    // fields
    private String username;
    private String password;
    private String system;
    private boolean enable;

    // collections
    private Set<CmsWebserviceCallRecord> webserviceCallRecords;


    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }


    public String getSystem() {
        return system;
    }


    public void setSystem(String system) {
        this.system = system;
    }


    public boolean isEnable() {
        return enable;
    }


    public void setEnable(boolean enable) {
        this.enable = enable;
    }


    public Set<CmsWebserviceCallRecord> getWebserviceCallRecords() {
        return webserviceCallRecords;
    }


    public void setWebserviceCallRecords(Set<CmsWebserviceCallRecord> webserviceCallRecords) {
        this.webserviceCallRecords = webserviceCallRecords;
    }


}