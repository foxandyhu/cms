package com.bfly.cms.content.entity;

import com.bfly.cms.member.entity.MemberGroup;
import com.bfly.cms.user.entity.User;
import com.bfly.core.enums.ContentStatus;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;
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
@Table(name = "content")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class Content implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    /**
     * 置顶级别
     */
    @Column(name = "top_level")
    private int topLevel;

    /**
     * 是否推荐
     */
    @Column(name = "is_recommend")
    private boolean recommend;

    /**
     * 状态
     *
     * @see ContentStatus
     */
    @Column(name = "status")
    private int status;

    /**
     * 推荐级别
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
    @OneToOne(mappedBy = "content", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    @NotFound(action = NotFoundAction.IGNORE)
    private ContentExt contentExt;

    /**
     * 文章具体内容及扩展内容
     */
    @OneToOne(mappedBy = "content", cascade = CascadeType.ALL, orphanRemoval = true)
    @PrimaryKeyJoinColumn
    @NotFound(action = NotFoundAction.IGNORE)
    private ContentTxt contentTxt;

    /**
     * 文章内容发布者
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private User publisher;

    /**
     * 文章内容浏览权限组
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "content_group_view", joinColumns = @JoinColumn(name = "content_id"), inverseJoinColumns = @JoinColumn(name = "group_id"))
    private Set<MemberGroup> viewGroups;

    /**
     * 文章内容图片集
     */
    @ElementCollection(fetch = FetchType.LAZY)
    @OrderColumn(name = "seq")
    @CollectionTable(name = "content_picture", joinColumns = @JoinColumn(name = "content_id"))
    private List<ContentPicture> pictures;


    /**
     * 文章内容附件
     */
    @ElementCollection(fetch = FetchType.LAZY)
    @OrderColumn(name = "seq")
    @CollectionTable(name = "content_attachment", joinColumns = @JoinColumn(name = "content_id"))
    private List<ContentAttachment> attachments;

    /**
     * 文章内容评分
     */
    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "content_id")
    private List<ContentScore> scoreRecordSet;

    /**
     * 文章内容扩展属性表
     */
    @ElementCollection
    @CollectionTable(name = "content_attr", joinColumns = @JoinColumn(name = "content_id"))
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
        ContentStatus status = ContentStatus.getStatus(getStatus());
        return status == null ? "" : status.getName();
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

    public ContentExt getContentExt() {
        return contentExt;
    }

    public void setContentExt(ContentExt contentExt) {
        this.contentExt = contentExt;
    }

    public ContentTxt getContentTxt() {
        return contentTxt;
    }

    public void setContentTxt(ContentTxt contentTxt) {
        this.contentTxt = contentTxt;
    }

    public User getPublisher() {
        return publisher;
    }

    public void setPublisher(User publisher) {
        this.publisher = publisher;
    }

    public Set<MemberGroup> getViewGroups() {
        return viewGroups;
    }

    public void setViewGroups(Set<MemberGroup> viewGroups) {
        this.viewGroups = viewGroups;
    }

    public List<ContentPicture> getPictures() {
        return pictures;
    }

    public void setPictures(List<ContentPicture> pictures) {
        this.pictures = pictures;
    }

    public List<ContentAttachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<ContentAttachment> attachments) {
        this.attachments = attachments;
    }

    public List<ContentScore> getScoreRecordSet() {
        return scoreRecordSet;
    }

    public void setScoreRecordSet(List<ContentScore> scoreRecordSet) {
        this.scoreRecordSet = scoreRecordSet;
    }

    public Map<String, String> getAttr() {
        return attr;
    }

    public void setAttr(Map<String, String> attr) {
        this.attr = attr;
    }
}