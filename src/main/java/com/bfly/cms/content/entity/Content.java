package com.bfly.cms.content.entity;

import com.bfly.cms.channel.entity.Channel;
import com.bfly.cms.comment.entity.Comment;
import com.bfly.cms.user.entity.User;
import com.bfly.cms.member.entity.MemberGroup;
import com.bfly.cms.words.entity.ContentTag;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

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
@Table(name = "content")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class Content implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * 排序日期
     */
    @Column(name = "sort_date")
    private Date sortDate;

    /**
     * 固顶级别
     */
    @Column(name = "top_level")
    private int topLevel;

    /**
     * 是否推荐
     */
    @Column(name = "is_recommend")
    private boolean recommend;

    /**
     * 状态(0:草稿;1:审核中;2:审核通过;3:回收站;4:投稿;5:归档)
     */
    @Column(name = "status")
    private int status;

    /**
     * 日访问数
     */
    @Column(name = "views_day")
    private int viewsDay;

    /**
     * 日评论数
     */
    @Column(name = "comments_day")
    private int commentsDay;

    /**
     * 日下载数
     */
    @Column(name = "downloads_day")
    private int downloadsDay;

    /**
     * 日顶数
     */
    @Column(name = "ups_day")
    private int upsDay;

    /**
     * 得分
     */
    @Column(name = "score")
    private int score;

    /**
     * 推荐级别
     */
    @Column(name = "recommend_level")
    private int recommendLevel;

    /**
     * 文章内容扩展信息
     */
    @OneToOne(cascade = CascadeType.REMOVE, mappedBy = "content")
    private ContentExt contentExt;

    /**
     * 文章内容统计数据
     */
    @OneToOne(cascade = CascadeType.REMOVE, mappedBy = "content")
    private ContentCount contentCount;

    /**
     * 文章内容类型
     */
    @ManyToOne
    @JoinColumn(name = "type_id")
    private ContentType type;

    /**
     * 文章内容发布者
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User publisher;

    /**
     * 文章内容所属频道
     */
    @ManyToOne
    @JoinColumn(name = "channel_id")
    private Channel channel;

    /**
     * 文章内容关联的专题
     */
    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
    @JoinTable(name = "content_topic", joinColumns = @JoinColumn(name = "content_id"), inverseJoinColumns = @JoinColumn(name = "topic_id"))
    private Set<Topic> topics;

    /**
     * 文章内容浏览权限组
     */
    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
    @JoinTable(name = "content_group_view", joinColumns = @JoinColumn(name = "content_id"), inverseJoinColumns = @JoinColumn(name = "group_id"))
    private Set<MemberGroup> viewGroups;

    /**
     * 文章内容关联的TAG标签
     */
    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
    @JoinTable(name = "content_tag_ship", joinColumns = @JoinColumn(name = "content_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private List<ContentTag> tags;


    /**
     * 文章内容图片集
     */
    @ElementCollection
    @OrderColumn(name = "priority")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
    @CollectionTable(name = "content_picture", joinColumns = @JoinColumn(name = "content_id"))
    private List<ContentPicture> pictures;


    /**
     * 文章内容附件
     */
    @ElementCollection
    @OrderColumn(name = "priority")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
    @CollectionTable(name = "content_attachment", joinColumns = @JoinColumn(name = "content_id"))
    private List<ContentAttachment> attachments;


    /**
     * 文章具体内容及扩展内容
     */
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
    @OneToOne(mappedBy = "content", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private ContentTxt contentTxt;

    /**
     * 文章内容扩展属性表
     */
    @ElementCollection
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
    @CollectionTable(name = "content_attr", joinColumns = @JoinColumn(name = "content_id"))
    @MapKeyColumn(name = "attr_name")
    @Column(name = "attr_value")
    private Map<String, String> attr;

    /**
     * 文章相关评论
     */
    @OneToMany(mappedBy = "content", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<Comment> comments;

    /**
     * 文章内容评分
     */
    @OneToMany(mappedBy = "content", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<ScoreRecord> scoreRecordSet;

    /**
     * 文章操作日志
     */
    @OneToMany(mappedBy = "content", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<ContentRecord> contentRecordSet;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getSortDate() {
        return sortDate;
    }

    public void setSortDate(Date sortDate) {
        this.sortDate = sortDate;
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

    public int getViewsDay() {
        return viewsDay;
    }

    public void setViewsDay(int viewsDay) {
        this.viewsDay = viewsDay;
    }

    public int getCommentsDay() {
        return commentsDay;
    }

    public void setCommentsDay(int commentsDay) {
        this.commentsDay = commentsDay;
    }

    public int getDownloadsDay() {
        return downloadsDay;
    }

    public void setDownloadsDay(int downloadsDay) {
        this.downloadsDay = downloadsDay;
    }

    public int getUpsDay() {
        return upsDay;
    }

    public void setUpsDay(int upsDay) {
        this.upsDay = upsDay;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getRecommendLevel() {
        return recommendLevel;
    }

    public void setRecommendLevel(int recommendLevel) {
        this.recommendLevel = recommendLevel;
    }

    public ContentExt getContentExt() {
        return contentExt;
    }

    public void setContentExt(ContentExt contentExt) {
        this.contentExt = contentExt;
    }

    public ContentCount getContentCount() {
        return contentCount;
    }

    public void setContentCount(ContentCount contentCount) {
        this.contentCount = contentCount;
    }

    public ContentType getType() {
        return type;
    }

    public void setType(ContentType type) {
        this.type = type;
    }

    public User getPublisher() {
        return publisher;
    }

    public void setPublisher(User publisher) {
        this.publisher = publisher;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public Set<Topic> getTopics() {
        return topics;
    }

    public void setTopics(Set<Topic> topics) {
        this.topics = topics;
    }

    public Set<MemberGroup> getViewGroups() {
        return viewGroups;
    }

    public void setViewGroups(Set<MemberGroup> viewGroups) {
        this.viewGroups = viewGroups;
    }

    public List<ContentTag> getTags() {
        return tags;
    }

    public void setTags(List<ContentTag> tags) {
        this.tags = tags;
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

    public ContentTxt getContentTxt() {
        return contentTxt;
    }

    public void setContentTxt(ContentTxt contentTxt) {
        this.contentTxt = contentTxt;
    }

    public Map<String, String> getAttr() {
        return attr;
    }

    public void setAttr(Map<String, String> attr) {
        this.attr = attr;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Set<ScoreRecord> getScoreRecordSet() {
        return scoreRecordSet;
    }

    public void setScoreRecordSet(Set<ScoreRecord> scoreRecordSet) {
        this.scoreRecordSet = scoreRecordSet;
    }

    public Set<ContentRecord> getContentRecordSet() {
        return contentRecordSet;
    }

    public void setContentRecordSet(Set<ContentRecord> contentRecordSet) {
        this.contentRecordSet = contentRecordSet;
    }
}