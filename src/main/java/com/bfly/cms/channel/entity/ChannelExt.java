package com.bfly.cms.channel.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * CMS栏目内容扩展
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/16 11:08
 */
@Entity
@Table(name = "channel_ext")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class ChannelExt implements Cloneable, Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "channel_id", unique = true, nullable = false)
    private int id;

    /**
     * 名称
     */
    @Column(name = "channel_name")
    private String name;


    /**
     * 是否栏目静态化
     */
    @Column(name = "is_static_channel")
    private boolean staticChannel;

    /**
     * 是否内容静态化
     */
    @Column(name = "is_static_content")
    private boolean staticContent;

    /**
     * 是否使用目录访问
     */
    @Column(name = "is_access_by_dir")
    private boolean accessByDir;

    /**
     * 每页多少条记录
     */
    @Column(name = "page_size")
    private int pageSize;

    /**
     * 栏目页生成规则
     */
    @Column(name = "channel_rule")
    private String channelRule;

    /**
     * 内容页生成规则
     */
    @Column(name = "content_rule")
    private String contentRule;

    /**
     * 外部链接
     */
    @Column(name = "link")
    private String link;

    /**
     * 栏目页模板
     */
    @Column(name = "tpl_channel")
    private String tplChannel;

    /**
     * 手机栏目页模板
     */
    @Column(name = "tpl_mobile_channel")
    private String tplMobileChannel;

    /**
     * 内容页模板
     */
    @Column(name = "tpl_content")
    private String tplContent;

    /**
     * 缩略图
     */
    @Column(name = "title_img")
    private String titleImg;

    /**
     * 内容图
     */
    @Column(name = "content_img")
    private String contentImg;

    /**
     * 内容是否有缩略图
     */
    @Column(name = "has_title_img")
    private boolean hasTitleImg;

    /**
     * 内容是否有内容图
     */
    @Column(name = "has_content_img")
    private boolean hasContentImg;

    /**
     * 内容标题图宽度
     */
    @Column(name = "title_img_width")
    private int titleImgWidth;

    /**
     * 内容标题图高度
     */
    @Column(name = "title_img_height")
    private int titleImgHeight;

    /**
     * 内容内容图宽度
     */
    @Column(name = "content_img_width")
    private int contentImgWidth;

    /**
     * 内容内容图高度
     */
    @Column(name = "content_img_height")
    private int contentImgHeight;

    /**
     * 评论(0:匿名;1:会员一次;2:关闭,3会员多次)
     */
    @Column(name = "comment_control")
    private int commentControl;

    /**
     * 顶踩(true:开放;false:关闭)
     */
    @Column(name = "allow_updown")
    private boolean allowUpdown;

    /**
     * 分享(true:开放;false:关闭)
     */
    @Column(name = "allow_share")
    private boolean allowShare;

    /**
     * 评分(true:开放;false:关闭)
     */
    @Column(name = "allow_score")
    private boolean allowScore;

    /**
     * 是否新窗口打开
     */
    @Column(name = "is_blank")
    private boolean blank;

    /**
     * TITLE
     */
    @Column(name = "title")
    private String title;

    /**
     * KEYWORDS
     */
    @Column(name = "keywords")
    private String keywords;

    /**
     * DESCRIPTION
     */
    @Column(name = "description")
    private String description;

    /**
     * 所属栏目
     */
    @OneToOne
    @MapsId
    @JoinColumn(name = "channel_id")
    private Channel channel;

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

    public boolean isStaticChannel() {
        return staticChannel;
    }

    public void setStaticChannel(boolean staticChannel) {
        this.staticChannel = staticChannel;
    }

    public boolean isStaticContent() {
        return staticContent;
    }

    public void setStaticContent(boolean staticContent) {
        this.staticContent = staticContent;
    }

    public boolean isAccessByDir() {
        return accessByDir;
    }

    public void setAccessByDir(boolean accessByDir) {
        this.accessByDir = accessByDir;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getChannelRule() {
        return channelRule;
    }

    public void setChannelRule(String channelRule) {
        this.channelRule = channelRule;
    }

    public String getContentRule() {
        return contentRule;
    }

    public void setContentRule(String contentRule) {
        this.contentRule = contentRule;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTplChannel() {
        return tplChannel;
    }

    public void setTplChannel(String tplChannel) {
        this.tplChannel = tplChannel;
    }

    public String getTplMobileChannel() {
        return tplMobileChannel;
    }

    public void setTplMobileChannel(String tplMobileChannel) {
        this.tplMobileChannel = tplMobileChannel;
    }

    public String getTplContent() {
        return tplContent;
    }

    public void setTplContent(String tplContent) {
        this.tplContent = tplContent;
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

    public boolean isHasTitleImg() {
        return hasTitleImg;
    }

    public void setHasTitleImg(boolean hasTitleImg) {
        this.hasTitleImg = hasTitleImg;
    }

    public boolean isHasContentImg() {
        return hasContentImg;
    }

    public void setHasContentImg(boolean hasContentImg) {
        this.hasContentImg = hasContentImg;
    }

    public int getTitleImgWidth() {
        return titleImgWidth;
    }

    public void setTitleImgWidth(int titleImgWidth) {
        this.titleImgWidth = titleImgWidth;
    }

    public int getTitleImgHeight() {
        return titleImgHeight;
    }

    public void setTitleImgHeight(int titleImgHeight) {
        this.titleImgHeight = titleImgHeight;
    }

    public int getContentImgWidth() {
        return contentImgWidth;
    }

    public void setContentImgWidth(int contentImgWidth) {
        this.contentImgWidth = contentImgWidth;
    }

    public int getContentImgHeight() {
        return contentImgHeight;
    }

    public void setContentImgHeight(int contentImgHeight) {
        this.contentImgHeight = contentImgHeight;
    }

    public int getCommentControl() {
        return commentControl;
    }

    public void setCommentControl(int commentControl) {
        this.commentControl = commentControl;
    }

    public boolean isAllowUpdown() {
        return allowUpdown;
    }

    public void setAllowUpdown(boolean allowUpdown) {
        this.allowUpdown = allowUpdown;
    }

    public boolean isAllowShare() {
        return allowShare;
    }

    public void setAllowShare(boolean allowShare) {
        this.allowShare = allowShare;
    }

    public boolean isAllowScore() {
        return allowScore;
    }

    public void setAllowScore(boolean allowScore) {
        this.allowScore = allowScore;
    }

    public boolean isBlank() {
        return blank;
    }

    public void setBlank(boolean blank) {
        this.blank = blank;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }
}