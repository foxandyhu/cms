package com.bfly.cms.content.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * CMS 文章内容扩展
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/16 13:23
 */
@Entity
@Table(name = "article_ext")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class ArticleExt implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "article", unique = true, nullable = false)
    private int id;

    @OneToOne
    @MapsId
    private Article article;
    /**
     * 标题
     */
    @Column(name = "title")
    private String title;

    /**
     * 简短标题
     */
    @Column(name = "short_title")
    private String shortTitle;

    /**
     * 描述
     */
    @Column(name = "remark")
    private String remark;

    /**
     * 作者
     */
    @Column(name = "author")
    private String author;

    /**
     * 来源
     */
    @Column(name = "origin")
    private String origin;

    /**
     * 来源链接
     */
    @Column(name = "origin_url")
    private String originUrl;

    /**
     * 发布日期
     */
    @Column(name = "post_date")
    private Date postDate;

    /**
     * 媒体路径
     */
    @Column(name = "media_path")
    private String mediaPath;

    /**
     * 媒体类型
     */
    @Column(name = "media_type")
    private String mediaType;

    /**
     * 标题颜色
     */
    @Column(name = "title_color")
    private String titleColor;

    /**
     * 是否加粗
     */
    @Column(name = "is_bold")
    private boolean bold;

    /**
     * 标题图片
     */
    @Column(name = "title_img")
    private String titleImg;

    /**
     * 内容图片
     */
    @Column(name = "content_img")
    private String contentImg;

    /**
     * 类型图片
     */
    @Column(name = "type_img")
    private String typeImg;

    /**
     * 外部链接
     */
    @Column(name = "link")
    private String link;

    /**
     * 指定模板
     * 为空时将采用所属栏目指定的模板
     */
    @Column(name = "tpl_pc")
    private String tplPc;

    /**
     * 手机内容页模板
     * 为空时将采用所属栏目指定的模板
     */
    @Column(name = "tpl_mobile")
    private String tplMobile;

    /**
     * 固顶到期日期
     */
    @Column(name = "toplevel_date")
    private Date topLevelDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortTitle() {
        return shortTitle;
    }

    public void setShortTitle(String shortTitle) {
        this.shortTitle = shortTitle;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getOriginUrl() {
        return originUrl;
    }

    public void setOriginUrl(String originUrl) {
        this.originUrl = originUrl;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    public String getMediaPath() {
        return mediaPath;
    }

    public void setMediaPath(String mediaPath) {
        this.mediaPath = mediaPath;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getTitleColor() {
        return titleColor;
    }

    public void setTitleColor(String titleColor) {
        this.titleColor = titleColor;
    }

    public boolean isBold() {
        return bold;
    }

    public void setBold(boolean bold) {
        this.bold = bold;
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

    public String getTypeImg() {
        return typeImg;
    }

    public void setTypeImg(String typeImg) {
        this.typeImg = typeImg;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
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

    public Date getTopLevelDate() {
        return topLevelDate;
    }

    public void setTopLevelDate(Date topLevelDate) {
        this.topLevelDate = topLevelDate;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }
}