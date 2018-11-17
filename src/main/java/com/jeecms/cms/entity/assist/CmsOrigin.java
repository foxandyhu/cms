package com.jeecms.cms.entity.assist;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 来源
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/16 17:40
 */
@Entity
@Table(name = "jc_origin")
public class CmsOrigin implements Serializable {
    private static final long serialVersionUID = 1L;

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
        if (getRefCount() != null) {
            json.put("refCount", getRefCount());
        } else {
            json.put("refCount", "");
        }
        return json;
    }

    @Id
    @Column(name = "origin_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 来源名称
     */
    @Column(name = "origin_name")
    private String name;

    /**
     * 来源文章总数
     */
    @Column(name = "ref_count")
    private Integer refCount;


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

    public Integer getRefCount() {
        return refCount;
    }


    public void setRefCount(Integer refCount) {
        this.refCount = refCount;
    }


}