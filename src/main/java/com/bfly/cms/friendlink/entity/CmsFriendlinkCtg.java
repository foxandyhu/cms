package com.bfly.cms.friendlink.entity;

import com.bfly.cms.siteconfig.entity.CmsSite;
import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import javax.persistence.*;
import java.io.Serializable;

/**
 * CMS友情链接类别
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/16 17:15
 */
@Entity
@Table(name = "jc_friendlink_ctg")
public class CmsFriendlinkCtg implements Serializable {
    private static final long serialVersionUID = 1L;

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
        return json;
    }

    public void init() {
        if (getPriority() == null) {
            setPriority(10);
        }
    }

    @Id
    @Column(name = "friendlinkctg_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 名称
     */
    @Column(name = "friendlinkctg_name")
    private String name;

    /**
     * 排列顺序
     */
    @Column(name = "priority")
    private Integer priority;

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


    public Integer getPriority() {
        return priority;
    }


    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public CmsSite getSite() {
        return site;
    }


    public void setSite(CmsSite site) {
        this.site = site;
    }


}