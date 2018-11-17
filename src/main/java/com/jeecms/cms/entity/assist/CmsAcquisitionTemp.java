package com.jeecms.cms.entity.assist;

import com.jeecms.core.entity.CmsSite;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 采集进度临时
 * @author andy_hulibo@163.com
 * @date 2018/11/16 16:42
 */
@Entity
@Table(name = "jc_acquisition_temp")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE,region = "beanCache")
public class CmsAcquisitionTemp implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "temp_id")
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
     *百分比
     */
    @Column(name = "finish_percent")
    private Integer percent;

    /**
     *描述
     */
    @Column(name = "description")
    private String description;

    /**
     *顺序
     */
    @Column(name = "seq")
    private Integer seq;

    @ManyToOne
    @JoinColumn(name="site_id")
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