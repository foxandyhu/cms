package com.jeecms.cms.entity.main;

import org.apache.commons.lang.StringUtils;
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
@Table(name = "jc_channel_ext")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class ChannelExt implements Cloneable, Serializable {

    private static final long serialVersionUID = 1L;
    public static String PROP_FINAL_STEP = "finalStep";
    public static String PROP_AFTER_CHECK = "afterCheck";
    
    /**
     * 游客评论
     */
    public static final int COMMENT_OPEN = 0;
    /**
     * 登录评论
     */
    public static final int COMMENT_LOGIN = 1;
    /**
     * 关闭评论
     */
    public static final int COMMENT_OFF = 2;
    /**
     * 登陆评论多次
     */
    public static final int COMMENT_LOGIN_MANY = 3;

    @Column(name = "channel_id")
    private Integer id;

    /**
     * 名称
     */
    @Column(name = "channel_name")
    private String name;

    /**
     * 终审级别
     */
    @Column(name = "final_step")
    private Byte finalStep;

    /**
     * 审核后(1:不能修改删除;2:修改后退回;3:修改后不变)
     */
    @Column(name = "after_check")
    private Byte afterCheck;

    /**
     * 是否栏目静态化
     */
    @Column(name = "is_static_channel")
    private Boolean staticChannel;

    /**
     * 是否内容静态化
     */
    @Column(name = "is_static_content")
    private Boolean staticContent;

    /**
     * 是否使用目录访问
     */
    @Column(name = "is_access_by_dir")
    private Boolean accessByDir;

    /**
     * 是否使用子栏目列表
     */
    @Column(name = "is_list_child")
    private Boolean listChild;

    /**
     * 每页多少条记录
     */
    @Column(name = "page_size")
    private Integer pageSize;

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
    private Boolean hasTitleImg;

    /**
     * 内容是否有内容图
     */
    @Column(name = "has_content_img")
    private Boolean hasContentImg;

    /**
     * 内容标题图宽度
     */
    @Column(name = "title_img_width")
    private Integer titleImgWidth;

    /**
     * 内容标题图高度
     */
    @Column(name = "title_img_height")
    private Integer titleImgHeight;

    /**
     * 内容内容图宽度
     */
    @Column(name = "content_img_width")
    private Integer contentImgWidth;

    /**
     * 内容内容图高度
     */
    @Column(name = "content_img_height")
    private Integer contentImgHeight;

    /**
     * 评论(0:匿名;1:会员一次;2:关闭,3会员多次)
     */
    @Column(name = "comment_control")
    private Integer commentControl;

    /**
     * 顶踩(true:开放;false:关闭)
     */
    @Column(name = "allow_updown")
    private Boolean allowUpdown;

    /**
     * 分享(true:开放;false:关闭)
     */
    @Column(name = "allow_share")
    private Boolean allowShare;

    /**
     * 评分(true:开放;false:关闭)
     */
    @Column(name = "allow_score")
    private Boolean allowScore;

    /**
     * 是否新窗口打开
     */
    @Column(name = "is_blank")
    private Boolean blank;

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

    @Id
    @OneToOne
    @JoinColumn(name = "channel_id")
    private Channel channel;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public Byte getFinalStep() {
        return finalStep;
    }


    public void setFinalStep(Byte finalStep) {
        this.finalStep = finalStep;
    }

    public Byte getAfterCheck() {
        return afterCheck;
    }

    public void setAfterCheck(Byte afterCheck) {
        this.afterCheck = afterCheck;
    }

    public Boolean getStaticChannel() {
        return staticChannel;
    }

    public void setStaticChannel(Boolean staticChannel) {
        this.staticChannel = staticChannel;
    }

    public Boolean getStaticContent() {
        return staticContent;
    }

    public void setStaticContent(Boolean staticContent) {
        this.staticContent = staticContent;
    }

    public Boolean getAccessByDir() {
        return accessByDir;
    }

    public void setAccessByDir(Boolean accessByDir) {
        this.accessByDir = accessByDir;
    }

    public Boolean getListChild() {
        return listChild;
    }

    public void setListChild(Boolean listChild) {
        this.listChild = listChild;
    }


    public Integer getPageSize() {
        return pageSize;
    }


    public void setPageSize(Integer pageSize) {
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

    public Boolean getHasTitleImg() {
        return hasTitleImg;
    }

    public void setHasTitleImg(Boolean hasTitleImg) {
        this.hasTitleImg = hasTitleImg;
    }

    public Boolean getHasContentImg() {
        return hasContentImg;
    }

    public void setHasContentImg(Boolean hasContentImg) {
        this.hasContentImg = hasContentImg;
    }

    public Integer getTitleImgWidth() {
        return titleImgWidth;
    }

    public void setTitleImgWidth(Integer titleImgWidth) {
        this.titleImgWidth = titleImgWidth;
    }

    public Integer getTitleImgHeight() {
        return titleImgHeight;
    }

    public void setTitleImgHeight(Integer titleImgHeight) {
        this.titleImgHeight = titleImgHeight;
    }

    public Integer getContentImgWidth() {
        return contentImgWidth;
    }

    public void setContentImgWidth(Integer contentImgWidth) {
        this.contentImgWidth = contentImgWidth;
    }

    public Integer getContentImgHeight() {
        return contentImgHeight;
    }

    public void setContentImgHeight(Integer contentImgHeight) {
        this.contentImgHeight = contentImgHeight;
    }

    public Integer getCommentControl() {
        return commentControl;
    }

    public void setCommentControl(Integer commentControl) {
        this.commentControl = commentControl;
    }

    public Boolean getAllowUpdown() {
        return allowUpdown;
    }

    public void setAllowUpdown(Boolean allowUpdown) {
        this.allowUpdown = allowUpdown;
    }

    public Boolean getAllowShare() {
        return allowShare;
    }

    public void setAllowShare(Boolean allowShare) {
        this.allowShare = allowShare;
    }

    public Boolean getAllowScore() {
        return allowScore;
    }

    public void setAllowScore(Boolean allowScore) {
        this.allowScore = allowScore;
    }

    public Boolean getBlank() {
        return blank;
    }

    public void setBlank(Boolean blank) {
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

    public void init() {
        if (getHasTitleImg() == null) {
            setHasTitleImg(false);
        }
        if (getHasContentImg() == null) {
            setHasContentImg(false);
        }
        if (getTitleImgWidth() == null) {
            setTitleImgWidth(139);
        }
        if (getTitleImgHeight() == null) {
            setTitleImgHeight(139);
        }
        if (getContentImgWidth() == null) {
            setContentImgWidth(310);
        }
        if (getContentImgHeight() == null) {
            setContentImgHeight(310);
        }
        if (getBlank() == null) {
            setBlank(false);
        }
        if (getCommentControl() == null) {
            setCommentControl(2);
        }
        if (getAllowUpdown() == null) {
            setAllowUpdown(true);
        }
        if (getStaticChannel() == null) {
            setStaticChannel(false);
        }
        if (getStaticContent() == null) {
            setStaticContent(false);
        }
        if (getAccessByDir() == null) {
            setAccessByDir(true);
        }
        if (getListChild() == null) {
            setListChild(false);
        }
        if (getPageSize() == null) {
            setPageSize(20);
        }
        if (getAllowScore() == null) {
            setAllowShare(false);
        }
        if (getAllowScore() == null) {
            setAllowScore(false);
        }
        blankToNull();
    }


    public Object clone() {
        ChannelExt c = null;
        try {
            c = (ChannelExt) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return c;
    }

    public void blankToNull() {
        if (StringUtils.isBlank(getLink())) {
            setLink(null);
        }
        if (StringUtils.isBlank(getTplChannel())) {
            setTplChannel(null);
        }
        if (StringUtils.isBlank(getTplContent())) {
            setTplContent(null);
        }
        if (StringUtils.isBlank(getTitle())) {
            setTitle(null);
        }
        if (StringUtils.isBlank(getKeywords())) {
            setKeywords(null);
        }
        if (StringUtils.isBlank(getDescription())) {
            setDescription(null);
        }
        if (StringUtils.isBlank(getTitleImg())) {
            setTitleImg(null);
        }
        if (StringUtils.isBlank(getContentImg())) {
            setContentImg(null);
        }
        if (StringUtils.isBlank(getChannelRule())) {
            setChannelRule(null);
        }
        if (StringUtils.isBlank(getContentRule())) {
            setContentRule(null);
        }
    }
}