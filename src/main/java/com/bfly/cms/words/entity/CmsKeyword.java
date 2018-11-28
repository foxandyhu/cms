package com.bfly.cms.words.entity;

import com.bfly.cms.siteconfig.entity.CmsSite;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.json.JSONObject;

import javax.persistence.*;
import java.io.Serializable;

/**
 * CMS内容关键词
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/16 17:31
 */
@Entity
@Table(name = "jc_keyword")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class CmsKeyword implements Serializable {
    private static final long serialVersionUID = 1L;

    public void init() {
        if (getDisabled() == null) {
            setDisabled(false);
        }
    }

    public JSONObject convertToJson() {
        JSONObject json = new JSONObject();
        if (getId() != null) {
            json.put("id", getId());
        } else {
            json.put("id", "");
        }
        if (StringUtils.isNotBlank(getName())) {
            json.put("name", getName());
        } else {
            json.put("name", "");
        }
        if (StringUtils.isNotBlank(getUrl())) {
            json.put("url", getUrl());
        } else {
            json.put("url", "");
        }
        if (getDisabled() != null) {
            json.put("disabled", getDisabled());
        } else {
            json.put("disabled", "");
        }
        return json;
    }

    @Id
    @Column(name = "keyword_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 名称
     */
    @Column(name = "keyword_name")
    private String name;

    /**
     * 链接
     */
    @Column(name = "url")
    private String url;

    /**
     * 是否禁用
     */
    @Column(name = "is_disabled")
    private Boolean disabled;

    @ManyToOne
    @JoinColumn(name = "site_id")
    private CmsSite site;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public CmsSite getSite() {
        return site;
    }


    public void setSite(CmsSite site) {
        this.site = site;
    }


}