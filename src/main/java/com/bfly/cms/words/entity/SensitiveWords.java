package com.bfly.cms.words.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * CMS敏感词
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/17 9:54
 */
@Entity
@Table(name = "d_sensitive_words")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class SensitiveWords implements Serializable {

    private static final long serialVersionUID = 5007411776086094322L;

    @Id
    @Column(name = "sensitivity_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * 敏感词
     */
    @Column(name = "word")
    @NotBlank(message = "敏感词不能为空!")
    private String word;

    /**
     * 替换词
     */
    @Column(name = "replace_word")
    @NotBlank(message = "替换词不能为空!")
    private String replaceWord;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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