package com.jeecms.cms.entity.assist;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;

import java.io.Serializable;


public class CmsSensitivity implements Serializable {
    private static final long serialVersionUID = 1L;

    public JSONObject convertToJson() {
        JSONObject json = new JSONObject();
        if (getId() != null) {
            json.put("id", getId());
        } else {
            json.put("id", "");
        }
        if (StringUtils.isNotBlank(getSearch())) {
            json.put("search", getSearch());
        } else {
            json.put("search", "");
        }
        if (StringUtils.isNotBlank(getReplacement())) {
            json.put("replacement", getReplacement());
        } else {
            json.put("replacement", "");
        }
        return json;
    }

    // primary key
    private Integer id;

    // fields
    private String search;
    private String replacement;


    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }

    public String getSearch() {
        return search;
    }


    public void setSearch(String search) {
        this.search = search;
    }

    public String getReplacement() {
        return replacement;
    }


    public void setReplacement(String replacement) {
        this.replacement = replacement;
    }


}