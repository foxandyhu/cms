package com.bfly.cms.acquisition.entity;

import com.bfly.cms.content.entity.Content;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 采集历史记录
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/16 16:32
 */
@Entity
@Table(name = "acquisition_history")
public class AcquisitionHistory implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * 栏目地址
     */
    @Column(name = "channel_url")
    private String channelUrl;

    /**
     * 内容地址
     */
    @Column(name = "content_url")
    private String contentUrl;

    /**
     * 标题
     */
    @Column(name = "title")
    private String title;

    /**
     * 描述
     */
    @Column(name = "description")
    private String description;

    /**
     * 所属采集器
     */
    @ManyToOne
    @JoinColumn(name = "acquisition_id")
    private Acquisition acquisition;

    /**
     * 所属文章内容
     */
    @ManyToOne
    @JoinColumn(name = "content_id")
    private Content content;


    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public Acquisition getAcquisition() {
        return acquisition;
    }

    public void setAcquisition(Acquisition acquisition) {
        this.acquisition = acquisition;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }
}