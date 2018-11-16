package com.jeecms.cms.entity.assist;

import com.jeecms.cms.entity.main.Content;
import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;

import java.io.Serializable;


public class CmsAcquisitionHistory implements Serializable {
    private static final long serialVersionUID = 1L;

    public JSONObject convertToJson() {
        JSONObject json = new JSONObject();
        if (getId() != null) {
            json.put("id", getId());
        } else {
            json.put("id", "");
        }
        if (StringUtils.isNotBlank(getChannelUrl())) {
            json.put("channelUrl", getChannelUrl());
        } else {
            json.put("channelUrl", "");
        }
        if (StringUtils.isNotBlank(getContentUrl())) {
            json.put("contentUrl", getContentUrl());
        } else {
            json.put("contentUrl", "");
        }
        if (StringUtils.isNotBlank(getTitle())) {
            json.put("title", getTitle());
        } else {
            json.put("title", "");
        }
        if (StringUtils.isNotBlank(getDescription())) {
            json.put("description", getDescription());
        } else {
            json.put("description", "");
        }
        if (getAcquisition() != null && StringUtils.isNotBlank(getAcquisition().getName())) {
            json.put("acqName", getAcquisition().getName());
        } else {
            json.put("acqName", "");
        }
        if (getAcquisition() != null && getAcquisition().getChannel() != null &&
                StringUtils.isNotBlank(getAcquisition().getChannel().getName())) {
            json.put("acqChannelName", getAcquisition().getChannel().getName());
        } else {
            json.put("acqChannelName", "");
        }
        if (getAcquisition() != null && getAcquisition().getType() != null &&
                StringUtils.isNotBlank(getAcquisition().getType().getName())) {
            json.put("acqTypeName", getAcquisition().getType().getName());
        } else {
            json.put("acqTypeName", "");
        }
        return json;
    }

    // primary key
    private Integer id;

    // fields
    private String channelUrl;
    private String contentUrl;
    private String title;
    private String description;

    // many to one
    private CmsAcquisition acquisition;
    private Content content;


    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }


    public String getChannelUrl() {
        return channelUrl;
    }


    public void setChannelUrl(String channelUrl) {
        this.channelUrl = channelUrl;
    }


    public String getContentUrl() {
        return contentUrl;
    }


    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }


    public String getTitle() {
        return title;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }


    public CmsAcquisition getAcquisition() {
        return acquisition;
    }

    public void setAcquisition(CmsAcquisition acquisition) {
        this.acquisition = acquisition;
    }


    public Content getContent() {
        return content;
    }


    public void setContent(Content content) {
        this.content = content;
    }


}