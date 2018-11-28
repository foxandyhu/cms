package com.bfly.cms.comment.entity;

import com.bfly.cms.siteconfig.entity.CmsSite;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.json.JSONException;
import org.json.JSONObject;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

/**
 * CMS留言类别
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/16 17:23
 */
@Entity
@Table(name = "jc_guestbook_ctg")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class CmsGuestbookCtg implements Serializable {
    private static final long serialVersionUID = 1L;

    public JSONObject convertToJson()
            throws JSONException {
        JSONObject json = new JSONObject();
        if (getId() != null) {
            json.put("id", getId());
        } else {
            json.put("id", "");
        }
        if (StringUtils.isNotBlank(getDescription())) {
            json.put("description", getDescription());
        } else {
            json.put("description", "");
        }
        if (StringUtils.isNotBlank(getName())) {
            json.put("name", getName());
        } else {
            json.put("name", "");
        }
        if (getPriority() != null) {
            json.put("priority", getPriority());
        } else {
            json.put("priority", "");
        }
        return json;
    }

    public void init() {
        if (getPriority() == null) {
            setPriority(10);
        }
    }

    public static Integer[] fetchIds(Collection<CmsGuestbookCtg> guestBookCtgs) {
        if (guestBookCtgs == null) {
            return null;
        }
        Integer[] ids = new Integer[guestBookCtgs.size()];
        int i = 0;
        for (CmsGuestbookCtg c : guestBookCtgs) {
            ids[i++] = c.getId();
        }
        return ids;
    }

    @Id
    @Column(name = "guestbookctg_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 名称
     */
    @Column(name = "ctg_name")
    private String name;

    /**
     * 排列顺序
     */
    @Column(name = "priority")
    private Integer priority;

    /**
     * 描述
     */
    @Column(name = "description")
    private String description;

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


    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }


    public CmsSite getSite() {
        return site;
    }


    public void setSite(CmsSite site) {
        this.site = site;
    }

}