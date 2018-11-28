package com.bfly.cms.acquisition.entity;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.json.JSONObject;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 采集内容替换
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/16 16:35
 */
@Entity
@Table(name = "jc_acquisition_replace")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class CmsAcquisitionReplace implements Serializable {

    public JSONObject convertToJson() {
        JSONObject json = new JSONObject();
        if (getId() != null) {
            json.put("id", getId());
        } else {
            json.put("id", "");
        }
        if (StringUtils.isNotBlank(getKeyword())) {
            json.put("keyword", getKeyword());
        } else {
            json.put("keyword", "");
        }
        if (StringUtils.isNotBlank(getReplaceWord())) {
            json.put("replaceWord", getReplaceWord());
        } else {
            json.put("replaceWord", "");
        }
        return json;
    }

    public void init() {

    }

    @Id
    @Column(name = "replace_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 关键词
     */
    @Column(name = "keyword")
    private String keyword;

    /**
     * 替换词
     */
    @Column(name = "replace_word")
    private String replaceWord;

    @ManyToOne
    @JoinColumn(name = "acquisition_id")
    private CmsAcquisition acquisition;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getReplaceWord() {
        return replaceWord;
    }

    public void setReplaceWord(String replaceWord) {
        this.replaceWord = replaceWord;
    }

    public CmsAcquisition getAcquisition() {
        return acquisition;
    }

    public void setAcquisition(CmsAcquisition acquisition) {
        this.acquisition = acquisition;
    }

}
