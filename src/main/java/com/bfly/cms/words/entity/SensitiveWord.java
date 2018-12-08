package com.bfly.cms.words.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * CMS敏感词
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/17 9:54
 */
@Entity
@Table(name = "d_sensitive_word")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class SensitiveWord implements Serializable {

    private static final long serialVersionUID = 5007411776086094322L;

    @Id
    @Column(name = "sensitivity_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 敏感词
     */
    @Column(name = "word")
    private String word;

    /**
     * 替换词
     */
    @Column(name = "replace_word")
    private String replaceWord;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getReplaceWord() {
        return replaceWord;
    }

    public void setReplaceWord(String replaceWord) {
        this.replaceWord = replaceWord;
    }
}