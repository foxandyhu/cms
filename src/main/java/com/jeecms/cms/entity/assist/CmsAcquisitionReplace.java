package com.jeecms.cms.entity.assist;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;

import java.io.Serializable;


public class CmsAcquisitionReplace implements Serializable {

    public JSONObject convertToJson() {
        JSONObject json = new JSONObject();
        if (getId() != null) {
            json.put("id", getId());
        } else {
            json.put("id", "");
        }
        if (StringUtils.isNotBlank(getKeyword())) {
            json.put("keyword", getKeyword());
        } else {
            json.put("keyword", "");
        }
        if (StringUtils.isNotBlank(getReplaceWord())) {
            json.put("replaceWord", getReplaceWord());
        } else {
            json.put("replaceWord", "");
        }
        return json;
    }

    public void init() {

    }

    // primary key
    private Integer id;

    // fields
    private String keyword;
    private String replaceWord;

    // many to one
    private CmsAcquisition acquisition;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getReplaceWord() {
        return replaceWord;
    }

    public void setReplaceWord(String replaceWord) {
        this.replaceWord = replaceWord;
    }

    public CmsAcquisition getAcquisition() {
        return acquisition;
    }

    public void setAcquisition(CmsAcquisition acquisition) {
        this.acquisition = acquisition;
    }

}
