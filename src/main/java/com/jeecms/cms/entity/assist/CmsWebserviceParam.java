package com.jeecms.cms.entity.assist;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;

import java.io.Serializable;


public class CmsWebserviceParam implements Serializable {
    private static final long serialVersionUID = 1L;

    public JSONObject convertToJson() {
        JSONObject json = new JSONObject();
        if (StringUtils.isNotBlank(getParamName())) {
            json.put("paramName", getParamName());
        } else {
            json.put("paramName", "");
        }
        if (StringUtils.isNotBlank(getDefaultValue())) {
            json.put("defaultValue", getDefaultValue());
        } else {
            json.put("defaultValue", "");
        }
        return json;
    }

    // fields
    private String paramName;
    private String defaultValue;

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }


    public String getDefaultValue() {
        return defaultValue;
    }


    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }


}