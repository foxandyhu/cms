package com.jeecms.cms.entity.assist;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;

import java.io.Serializable;

public class CmsAcquisitionShield implements Serializable {

    public JSONObject convertToJson() {
        JSONObject json = new JSONObject();
        if (getId() != null) {
            json.put("id", getId());
        } else {
            json.put("id", "");
        }
        if (StringUtils.isNotBlank(getShieldStart())) {
            json.put("shieldStart", getShieldStart());
        } else {
            json.put("shieldStart", "");
        }
        if (StringUtils.isNotBlank(getShieldEnd())) {
            json.put("shieldEnd", getShieldEnd());
        } else {
            json.put("shieldEnd", "");
        }
        return json;
    }

    // primary key
    private Integer id;

    // fields
    private String shieldStart;
    private String shieldEnd;

    // many to one
    private CmsAcquisition acquisition;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getShieldStart() {
        return shieldStart;
    }

    public void setShieldStart(String shieldStart) {
        this.shieldStart = shieldStart;
    }

    public String getShieldEnd() {
        return shieldEnd;
    }

    public void setShieldEnd(String shieldEnd) {
        this.shieldEnd = shieldEnd;
    }

    public CmsAcquisition getAcquisition() {
        return acquisition;
    }

    public void setAcquisition(CmsAcquisition acquisition) {
        this.acquisition = acquisition;
    }

}
