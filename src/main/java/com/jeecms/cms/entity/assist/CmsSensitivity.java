package com.jeecms.cms.entity.assist;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.json.JSONObject;

import javax.persistence.*;
import java.io.Serializable;

/**
 * CMS敏感词
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/17 9:54
 */
@Entity
@Table(name = "jc_sensitivity")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class CmsSensitivity implements Serializable {
    private static final long serialVersionUID = 1L;

    public JSONObject convertToJson() {
        JSONObject json = new JSONObject();
        if (getId() != null) {
            json.put("id", getId());
        } else {
            json.put("id", "");
        }
        if (StringUtils.isNotBlank(getSearch())) {
            json.put("search", getSearch());
        } else {
            json.put("search", "");
        }
        if (StringUtils.isNotBlank(getReplacement())) {
            json.put("replacement", getReplacement());
        } else {
            json.put("replacement", "");
        }
        return json;
    }

    @Id
    @Column(name = "sensitivity_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 敏感词
     */
    @Column(name = "search")
    private String search;

    /**
     * 替换词
     */
    @Column(name = "replacement")
    private String replacement;


    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }

    public String getSearch() {
        return search;
    }


    public void setSearch(String search) {
        this.search = search;
    }

    public String getReplacement() {
        return replacement;
    }


    public void setReplacement(String replacement) {
        this.replacement = replacement;
    }


}