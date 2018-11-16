package com.jeecms.cms.entity.assist;

import com.jeecms.core.entity.CmsSite;

import java.io.Serializable;


public class CmsAcquisitionTemp implements Serializable {
    private static final long serialVersionUID = 1L;
    // primary key
    private Integer id;

    // fields
    private String channelUrl;
    private String contentUrl;
    private String title;
    private Integer percent;
    private String description;
    private Integer seq;

    // many to one
    private CmsSite site;

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

    public Integer getPercent() {
        return percent;
    }

    public void setPercent(Integer percent) {
        this.percent = percent;
    }

    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }


    public Integer getSeq() {
        return seq;
    }


    public void setSeq(Integer seq) {
        this.seq = seq;
    }


    public CmsSite getSite() {
        return site;
    }


    public void setSite(CmsSite site) {
        this.site = site;
    }


}