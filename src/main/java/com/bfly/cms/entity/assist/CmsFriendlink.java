package com.bfly.cms.entity.assist;

import com.bfly.core.entity.CmsSite;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.json.JSONException;
import org.json.JSONObject;

import javax.persistence.*;
import java.io.Serializable;

/**
 * CMS友情链接
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/16 17:12
 */
@Entity
@Table(name = "jc_friendlink")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class CmsFriendlink implements Serializable {
    private static final long serialVersionUID = 1L;

    public void init() {
        if (getPriority() == null) {
            setPriority(10);
        }
        if (getViews() == null) {
            setViews(0);
        }
        if (getEnabled() == null) {
            setEnabled(true);
        }
        blankToNull();
    }

    public void blankToNull() {
        if (StringUtils.isBlank(getLogo())) {
            setLogo(null);
        }
    }

    public JSONObject convertToJson()
            throws JSONException {
        JSONObject json = new JSONObject();
        if (getId() != null) {
            json.put("id", getId());
        } else {
            json.put("id", "");
        }
        if (getPriority() != null) {
            json.put("priority", getPriority());
        } else {
            json.put("priority", "");
        }
        if (StringUtils.isNotBlank(getName())) {
            json.put("name", getName());
        } else {
            json.put("name", "");
        }
        if (StringUtils.isNotBlank(getDomain())) {
            json.put("domain", getDomain());
        } else {
            json.put("domain", "");
        }
        if (StringUtils.isNotBlank(getLogo())) {
            json.put("logo", getLogo());
        } else {
            json.put("logo", "");
        }
        if (StringUtils.isNotBlank(getEmail())) {
            json.put("email", getEmail());
        } else {
            json.put("email", "");
        }
        if (StringUtils.isNotBlank(getDescription())) {
            json.put("description", getDescription());
        } else {
            json.put("description", "");
        }
        if (getViews() != null) {
            json.put("views", getViews());
        } else {
            json.put("views", "");
        }
        if (getEnabled() != null) {
            json.put("enabled", getEnabled());
        } else {
            json.put("enabled", "");
        }
        if (getCategory() != null && StringUtils.isNotBlank(getCategory().getName())) {
            json.put("categoryName", getCategory().getName());
        } else {
            json.put("categoryName", "");
        }
        if (getCategory() != null && getCategory().getId() != null) {
            json.put("categoryId", getCategory().getId());
        } else {
            json.put("categoryId", "");
        }
        return json;
    }

    @Id
    @Column(name = "friendlink_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 网站名称
     */
    @Column(name = "site_name")
    private String name;

    /**
     * 网站地址
     */
    @Column(name = "domain")
    private String domain;

    /**
     * 图标
     */
    @Column(name = "logo")
    private String logo;

    /**
     * 站长邮箱
     */
    @Column(name = "email")
    private String email;

    /**
     * 描述
     */
    @Column(name = "description")
    private String description;

    /**
     * 点击次数
     */
    @Column(name = "views")
    private Integer views;

    /**
     * 排列顺序
     */
    @Column(name = "priority")
    private Integer priority;

    /**
     * 是否显示
     */
    @Column(name = "is_enabled")
    private Boolean enabled;

    @ManyToOne
    @JoinColumn(name = "friendlinkctg_id")
    private CmsFriendlinkCtg category;

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


    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Boolean getEnabled() {
        return enabled;
    }


    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public CmsFriendlinkCtg getCategory() {
        return category;
    }

    public void setCategory(CmsFriendlinkCtg category) {
        this.category = category;
    }

    public CmsSite getSite() {
        return site;
    }


    public void setSite(CmsSite site) {
        this.site = site;
    }

}