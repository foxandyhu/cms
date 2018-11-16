package com.jeecms.cms.entity.assist;

import com.jeecms.common.util.DateUtils;
import com.jeecms.common.web.springmvc.MessageResolver;
import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Date;


public class CmsWebserviceCallRecord implements Serializable {
    private static final long serialVersionUID = 1L;

    public JSONObject convertToJson(HttpServletRequest request) {
        JSONObject json = new JSONObject();
        if (getId() != null) {
            json.put("id", getId());
        } else {
            json.put("id", "");
        }
        if (StringUtils.isNotBlank(getServiceCode())) {
            json.put("serviceCode", MessageResolver.getMessage(request, "cmsWebservice.call." + getServiceCode()));
        } else {
            json.put("serviceCode", "");
        }
        if (getRecordTime() != null) {
            json.put("recordTime", DateUtils.parseDateToDateStr(getRecordTime()));
        } else {
            json.put("recordTime", "");
        }
        if (getAuth() != null && StringUtils.isNotBlank(getAuth().getUsername())) {
            json.put("authUsername", getAuth().getUsername());
        } else {
            json.put("authUsername", "");
        }
        if (getAuth() != null && StringUtils.isNotBlank(getAuth().getSystem())) {
            json.put("authSystem", getAuth().getSystem());
        } else {
            json.put("authSystem", "");
        }
        return json;
    }

    // primary key
    private Integer id;

    // fields
    private String serviceCode;
    private Date recordTime;

    // many to one
    private CmsWebserviceAuth auth;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getServiceCode() {
        return serviceCode;
    }


    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }


    public Date getRecordTime() {
        return recordTime;
    }


    public void setRecordTime(Date recordTime) {
        this.recordTime = recordTime;
    }


    public CmsWebserviceAuth getAuth() {
        return auth;
    }


    public void setAuth(CmsWebserviceAuth auth) {
        this.auth = auth;
    }

}