package com.bfly.cms.content.entity;

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
@Table(name = "content_txt")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class ContentTxt implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "content_id", unique = true, nullable = false)
    private int id;

    /**
     * 文章内容
     */
    @Column(name = "txt")
    private String txt;

    /**
     * 扩展内容1
     */
    @Column(name = "txt1")
    private String txt1;

    /**
     * 扩展内容2
     */
    @Column(name = "txt2")
    private String txt2;

    /**
     * 扩展内容3
     */
    @Column(name = "txt3")
    private String txt3;

    /**
     * 所属文章
     */
    @OneToOne
    @MapsId
    @JoinColumn(name = "content_id")
    private Content content;


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

    public String getTxt1() {
        return txt1;
    }

    public void setTxt1(String txt1) {
        this.txt1 = txt1;
    }

    public String getTxt2() {
        return txt2;
    }

    public void setTxt2(String txt2) {
        this.txt2 = txt2;
    }

    public String getTxt3() {
        return txt3;
    }

    public void setTxt3(String txt3) {
        this.txt3 = txt3;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }
}