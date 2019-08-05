package com.bfly.cms.content.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * CMS专题类
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/16 11:50
 */
@Entity
@Table(name = "special_topic")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
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
    @Column(name = "description")
    private String description;

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
     * 专题模板
     */
    @Column(name = "tpl_content")
    private String tplContent;

    /**
     * 排列顺序
     */
    @Column(name = "priority")
    private int priority;

    /**
     * 是否推荐
     */
    @Column(name = "is_recommend")
    private boolean recommend;

    /**
     * 首字母拼音简写
     */
    @Column(name = "initials")
    private String initials;

    /**
     * 所属频道
     */
    @ManyToOne
    @JoinColumn(name = "channel_id")
    private Channel channel;

    /**
     * 专题关联的文章
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "special_topic_content_ship", joinColumns = @JoinColumn(name = "topic_id"), inverseJoinColumns = @JoinColumn(name = "content_id"))
    private Set<Content> contents;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getTplContent() {
        return tplContent;
    }

    public void setTplContent(String tplContent) {
        this.tplContent = tplContent;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public boolean isRecommend() {
        return recommend;
    }

    public void setRecommend(boolean recommend) {
        this.recommend = recommend;
    }

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public Set<Content> getContents() {
        return contents;
    }

    public void setContents(Set<Content> contents) {
        this.contents = contents;
    }
}