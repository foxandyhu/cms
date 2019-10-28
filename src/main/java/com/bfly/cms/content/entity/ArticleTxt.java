package com.bfly.cms.content.entity;

import com.alibaba.fastjson.annotation.JSONField;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * CMS内容文本
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/16 13:39
 */
@Entity
@Table(name = "article_txt")
public class ArticleTxt implements Serializable {


    private static final long serialVersionUID = 1285660022162142857L;
    @Id
    @Column(name = "article_id", unique = true, nullable = false)
    private int id;

    /**
     * 文章内容
     */
    @Column(name = "txt")
    private String txt;

    /**
     * 所属文章
     */
    @OneToOne
    @MapsId
    @JSONField(serialize = false)
    private Article article;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }
}