package com.bfly.cms.entity.assist;

import com.bfly.cms.entity.main.Content;
import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 采集历史记录
 * @author andy_hulibo@163.com
 * @date 2018/11/16 16:32
 */
@Entity
@Table(name = "jc_acquisition_history")
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

    @Id
    @Column(name = "history_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     *栏目地址
     */
    @Column(name = "channel_url")
    private String channelUrl;

    /**
     *内容地址
     */
    @Column(name = "content_url")
    private String contentUrl;

    /**
     *标题
     */
    @Column(name = "title")
    private String title;

    /**
     *描述
     */
    @Column(name = "description")
    private String description;

    /**
     * 采集源
     */
    @ManyToOne
    @JoinColumn(name = "acquisition_id")
    private CmsAcquisition acquisition;

    /**
     * 内容
     */
    @ManyToOne
    @JoinColumn(name = "content_id")
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