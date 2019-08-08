package com.bfly.cms.content.entity;

import com.bfly.cms.member.entity.MemberGroup;
import com.bfly.core.enums.ArticleStatus;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * CMS 文章内容
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/16 12:02
 */
@Entity
@Table(name = "article")
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    /**
     * 置顶级别---级别越高越靠前
     */
    @Column(name = "top_level")
    private int topLevel;

    /**
     * 置顶失效期--指定日期到期自动失效 不指定长期置顶
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/7 16:31
     */
    @Column(name = "top_expired")
    private Date topExpired;

    /**
     * 是否推荐
     */
    @Column(name = "is_recommend")
    private boolean recommend;

    /**
     * 状态
     *
     * @see ArticleStatus
     */
    @Column(name = "status")
    private int status;

    /**
     * 推荐级别-----级别越高越靠前
     */
    @Column(name = "recommend_level")
    private int recommendLevel;

    /**
     * 总访问数
     */
    @Column(name = "views")
    private int views;

    /**
     * 总评论数
     */
    @Column(name = "comments")
    private int comments;


    /**
     * 总下载数
     */
    @Column(name = "downloads")
    private int downloads;


    /**
     * 总顶数
     */
    @Column(name = "ups")
    private int ups;

    /**
     * 总踩数
     */
    @Column(name = "downs")
    private int downs;

    /**
     * 文章内容类型
     *
     * @see com.bfly.core.enums.ContentType
     */
    @Column(name = "type_id")
    private int type;

    /**
     * 文章内容所属频道
     */
    @Column(name = "channel_id")
    private int channelId;

    /**
     * 文章内容扩展信息
     */
    @OneToOne(mappedBy = "article", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    @NotFound(action = NotFoundAction.IGNORE)
    private ArticleExt articleExt;

    /**
     * 文章具体内容及扩展内容
     */
    @OneToOne(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    @PrimaryKeyJoinColumn
    @NotFound(action = NotFoundAction.IGNORE)
    private ArticleTxt articleTxt;

    /**
     * 文章内容发布者
     */
    private Integer userId;

    /**
     * 文章内容浏览权限组
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "article_group_view", joinColumns = @JoinColumn(name = "article_id"), inverseJoinColumns = @JoinColumn(name = "group_id"))
    private Set<MemberGroup> viewGroups;

    /**
     * 文章内容图片集
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private List<ArticlePicture> pictures;


    /**
     * 文章内容附件
     */
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private List<ArticleAttachment> attachments;

    /**
     * 文章内容扩展属性表
     */
    @ElementCollection
    @CollectionTable(name = "article_attr", joinColumns = @JoinColumn(name = "article_id"))
    @MapKeyColumn(name = "attr_name")
    @Column(name = "attr_value")
    private Map<String, String> attr;

    /**
     * 返回状态名称
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/1 13:51
     */
    public String getStatusName() {
        ArticleStatus status = ArticleStatus.getStatus(getStatus());
        return status == null ? "" : status.getName();
    }

    public Date getTopExpired() {
        return topExpired;
    }

    public void setTopExpired(Date topExpired) {
        this.topExpired = topExpired;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTopLevel() {
        return topLevel;
    }

    public void setTopLevel(int topLevel) {
        this.topLevel = topLevel;
    }

    public boolean isRecommend() {
        return recommend;
    }

    public void setRecommend(boolean recommend) {
        this.recommend = recommend;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getRecommendLevel() {
        return recommendLevel;
    }

    public void setRecommendLevel(int recommendLevel) {
        this.recommendLevel = recommendLevel;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public int getDownloads() {
        return downloads;
    }

    public void setDownloads(int downloads) {
        this.downloads = downloads;
    }

    public int getUps() {
        return ups;
    }

    public void setUps(int ups) {
        this.ups = ups;
    }

    public int getDowns() {
        return downs;
    }

    public void setDowns(int downs) {
        this.downs = downs;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    public ArticleExt getArticleExt() {
        return articleExt;
    }

    public void setArticleExt(ArticleExt articleExt) {
        this.articleExt = articleExt;
    }

    public ArticleTxt getArticleTxt() {
        return articleTxt;
    }

    public void setArticleTxt(ArticleTxt articleTxt) {
        this.articleTxt = articleTxt;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Set<MemberGroup> getViewGroups() {
        return viewGroups;
    }

    public void setViewGroups(Set<MemberGroup> viewGroups) {
        this.viewGroups = viewGroups;
    }

    public List<ArticlePicture> getPictures() {
        return pictures;
    }

    public void setPictures(List<ArticlePicture> pictures) {
        this.pictures = pictures;
    }

    public List<ArticleAttachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<ArticleAttachment> attachments) {
        this.attachments = attachments;
    }

    public Map<String, String> getAttr() {
        return attr;
    }

    public void setAttr(Map<String, String> attr) {
        this.attr = attr;
    }
}