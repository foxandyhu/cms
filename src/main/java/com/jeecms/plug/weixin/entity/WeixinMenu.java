package com.jeecms.plug.weixin.entity;

import com.jeecms.core.entity.CmsSite;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cache;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;

/**
 * 微信菜单
 * @author andy_hulibo@163.com
 * @date 2018/11/15 14:57
 */
@Entity
@Table(name = "jg_weixinmenu")
public class WeixinMenu implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "wm_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 微信菜单名称
     */
    @Column(name = "wm_name")
    private String name;

    /**
     * 微信菜单类别
     */
    @Column(name = "wm_type")
    private String type;

    /**
     * 链接地址
     */
    @Column(name = "wm_url")
    private String url;

    /**
     * 点击key
     */
    @Column(name = "wm_key")
    private String key;

    @ManyToOne
    @JoinColumn(name = "wm_parent_id")
    private WeixinMenu parent;

    @OneToMany(mappedBy = "parent",cascade = CascadeType.REMOVE)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<WeixinMenu> child;

    /**
     * 所属站点
     */
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public String getUrl() {
        return url;
    }


    public void setUrl(String url) {
        this.url = url;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }


    public WeixinMenu getParent() {
        return parent;
    }

    public void setParent(WeixinMenu parent) {
        this.parent = parent;
    }

    public CmsSite getSite() {
        return site;
    }


    public void setSite(CmsSite site) {
        this.site = site;
    }


    public Set<WeixinMenu> getChild() {
        return child;
    }


    public void setChild(Set<WeixinMenu> child) {
        this.child = child;
    }

    public void addTochild(WeixinMenu weixinMenu) {
        if (null == getChild()) setChild(new TreeSet<WeixinMenu>());
        getChild().add(weixinMenu);
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
        if (StringUtils.isNotBlank(getType())) {
            json.put("type", getType());
        } else {
            json.put("type", "");
        }
        if (StringUtils.isNotBlank(getUrl())) {
            json.put("url", getUrl());
        } else {
            json.put("url", "");
        }
        if (StringUtils.isNotBlank(getKey())) {
            json.put("key", getKey());
        } else {
            json.put("key", "");
        }
        return json;
    }
}