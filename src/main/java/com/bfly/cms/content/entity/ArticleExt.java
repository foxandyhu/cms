package com.bfly.cms.content.entity;

import com.alibaba.fastjson.annotation.JSONField;

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
public class ArticleExt implements Serializable {

    private static final long serialVersionUID = -1718867062465298590L;

    @Id
    @Column(name = "article", unique = true, nullable = false)
    private int id;

    @OneToOne
    @MapsId
    @JSONField(serialize = false)
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
     * 摘要
     */
    @Column(name = "summary")
    private String summary;

    /**
     * SEO KEYWORDS
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/9 15:42
     */
    @Column(name = "keywords")
    private String keywords;

    /**
     * SEO DESCRIPTION
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/9 15:59
     */
    @Column(name = "description")
    private String description;

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
     * 文件路径
     */
    @Column(name = "file_path")
    private String filePath;

    /**
     * 文件类型
     */
    @Column(name = "file_type")
    private String fileType;

    /**
     * 文件大小--媒体类型的是时长
     */
    @Column(name = "file_length")
    private String fileLength;

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
     * 标签
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/10 9:49
     */
    @Column(name = "tags")
    private String tags;

    public String getFileLength() {
        return fileLength;
    }

    public void setFileLength(String fileLength) {
        this.fileLength = fileLength;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getShortTitle() {
        return shortTitle;
    }

    public void setShortTitle(String shortTitle) {
        this.shortTitle = shortTitle;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
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

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
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

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }
}