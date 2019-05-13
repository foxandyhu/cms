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
@Table(name = "content_ext")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class ContentExt implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "content_id", unique = true, nullable = false)
    private int id;

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
    @Column(name = "release_date")
    private Date releaseDate;

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
     */
    @Column(name = "tpl_content")
    private String tplContent;

    /**
     * 手机内容页模板
     */
    @Column(name = "tpl_mobile_content")
    private String tplMobileContent;

    /**
     * 需要重新生成静态页
     */
    @Column(name = "need_regenerate")
    private boolean needRegenerate;

    /**
     * 固顶到期日期
     */
    @Column(name = "toplevel_date")
    private Date topLevelDate;

    /**
     * 归档日期
     */
    @Column(name = "pigeonhole_date")
    private Date pigeonholeDate;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
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

    public String getTplContent() {
        return tplContent;
    }

    public void setTplContent(String tplContent) {
        this.tplContent = tplContent;
    }

    public String getTplMobileContent() {
        return tplMobileContent;
    }

    public void setTplMobileContent(String tplMobileContent) {
        this.tplMobileContent = tplMobileContent;
    }

    public boolean isNeedRegenerate() {
        return needRegenerate;
    }

    public void setNeedRegenerate(boolean needRegenerate) {
        this.needRegenerate = needRegenerate;
    }

    public Date getTopLevelDate() {
        return topLevelDate;
    }

    public void setTopLevelDate(Date topLevelDate) {
        this.topLevelDate = topLevelDate;
    }

    public Date getPigeonholeDate() {
        return pigeonholeDate;
    }

    public void setPigeonholeDate(Date pigeonholeDate) {
        this.pigeonholeDate = pigeonholeDate;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }
}