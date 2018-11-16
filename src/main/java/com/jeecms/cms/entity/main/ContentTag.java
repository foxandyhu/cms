package com.jeecms.cms.entity.main;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.json.JSONException;
import org.json.JSONObject;

import javax.persistence.*;
import java.io.Serializable;

/**
 * CMS内容TAG
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/16 13:35
 */
@Entity
@Table(name = "jc_content_tag")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class ContentTag implements Serializable {
    private static final long serialVersionUID = 1L;

    public void init() {
        if (getCount() == null) {
            setCount(0);
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
        if (getCount() != null) {
            json.put("count", getCount());
        } else {
            json.put("count", "");
        }
        if (StringUtils.isNotBlank(getName())) {
            json.put("name", getName());
        } else {
            json.put("name", "");
        }
        return json;
    }


    @Id
    @Column(name = "tag_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * tag名称
     */
    @Column(name = "tag_name")
    private String name;

    /**
     * 被引用的次数
     */
    @Column(name = "ref_counter")
    private Integer count;


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

    public Integer getCount() {
        return count;
    }


    public void setCount(Integer count) {
        this.count = count;
    }

}