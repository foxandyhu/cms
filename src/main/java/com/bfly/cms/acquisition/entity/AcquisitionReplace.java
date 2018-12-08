package com.bfly.cms.acquisition.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 采集内容替换
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/16 16:35
 */
@Entity
@Table(name = "acquisition_replace")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class AcquisitionReplace implements Serializable {

    private static final long serialVersionUID = -1357001995481060571L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

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

    /**
     * 关联的采集器
     */
    @ManyToOne
    @JoinColumn(name = "acquisition_id")
    private Acquisition acquisition;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public Acquisition getAcquisition() {
        return acquisition;
    }

    public void setAcquisition(Acquisition acquisition) {
        this.acquisition = acquisition;
    }
}
