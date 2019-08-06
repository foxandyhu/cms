package com.bfly.cms.content.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * CMS专题类
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/16 11:50
 */
@Entity
@Table(name = "special_topic")
public class SpecialTopic implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * 名称
     */
    @Column(name = "topic_name")
    @NotBlank(message = "专题名称不能为空!")
    private String name;

    /**
     * 简称
     */
    @Column(name = "short_name")
    private String shortName;

    /**
     * 关键字
     */
    @Column(name = "keywords")
    private String keywords;

    /**
     * 描述
     */
    @Column(name = "remark")
    private String remark;

    /**
     * 标题图
     */
    @Column(name = "title_img")
    private String titleImg;

    /**
     * 内容图
     */
    @Column(name = "content_img")
    private String contentImg;

    /**
     * PC专题模板
     */
    @Column(name = "tpl_pc")
    private String tplPc;

    /**
     * Mobile专题模板
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/6 16:42
     */
    @Column(name = "tpl_mobile")
    private String tplMobile;

    /**
     * 排列顺序
     */
    @Column(name = "seq")
    private int seq;

    /**
     * 是否推荐
     */
    @Column(name = "is_recommend")
    private boolean recommend;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public String getTitleImg() {
        return titleImg;
    }

    public void setTitleImg(String titleImg) {
        this.titleImg = titleImg;
    }

    public String getContentImg() {
        return contentImg;
    }

    public void setContentImg(String contentImg) {
        this.contentImg = contentImg;
    }

    public String getTplPc() {
        return tplPc;
    }

    public void setTplPc(String tplPc) {
        this.tplPc = tplPc;
    }

    public String getTplMobile() {
        return tplMobile;
    }

    public void setTplMobile(String tplMobile) {
        this.tplMobile = tplMobile;
    }

    public boolean isRecommend() {
        return recommend;
    }

    public void setRecommend(boolean recommend) {
        this.recommend = recommend;
    }
}