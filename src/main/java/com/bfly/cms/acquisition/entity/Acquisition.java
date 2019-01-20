package com.bfly.cms.acquisition.entity;

import com.bfly.cms.channel.entity.Channel;
import com.bfly.cms.content.entity.ContentType;
import com.bfly.cms.user.entity.User;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * 文章采集器
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/16 16:14
 */
@Entity
@Table(name = "acquisition")
public class Acquisition implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * 采集名称
     */
    @Column(name = "acq_name")
    @NotBlank(message = "采集器名称不能为空!")
    private String name;

    /**
     * 开始时间
     */
    @Column(name = "start_time")
    private Date startTime;

    /**
     * 停止时间
     */
    @Column(name = "end_time")
    private Date endTime;

    /**
     * 当前状态(0:静止;1:采集;2:暂停)
     */
    @Column(name = "status")
    private int status;

    /**
     * 当前号码
     */
    @Column(name = "curr_num")
    private int currNum;

    /**
     * 当前条数
     */
    @Column(name = "curr_item")
    private int currItem;

    /**
     * 每页总条数
     */
    @Column(name = "total_item")
    private int totalItem;

    /**
     * 暂停时间(毫秒)
     */
    @Column(name = "pause_time")
    @Min(value = 500, message = "暂停时间不能小于500毫秒!")
    private int pauseTime;

    /**
     * 是否采集图片
     */
    @Column(name = "img_acqu")
    private int imgAcqu;

    /**
     * 页面编码
     */
    @Column(name = "page_encoding")
    @NotBlank(message = "页面编码不能为空!")
    private String pageEncoding;

    /**
     * 采集列表
     */
    @Column(name = "plan_list")
    private String planList;

    /**
     * 动态地址
     */
    @Column(name = "dynamic_addr")
    private String dynamicAddr;

    /**
     * 页码开始
     */
    @Column(name = "dynamic_start")
    private int dynamicStart;

    /**
     * 页码结束
     */
    @Column(name = "dynamic_end")
    private int dynamicEnd;

    /**
     * 内容链接区开始
     */
    @Column(name = "linkset_start")
    private String linksetStart;

    /**
     * 内容链接区结束
     */
    @Column(name = "linkset_end")
    private String linksetEnd;

    /**
     * 内容链接开始
     */
    @Column(name = "link_start")
    private String linkStart;

    /**
     * 内容链接结束
     */
    @Column(name = "link_end")
    private String linkEnd;

    /**
     * 标题开始
     */
    @Column(name = "title_start")
    private String titleStart;

    /**
     * 标题结束
     */
    @Column(name = "title_end")
    private String titleEnd;

    /**
     * 关键字开始
     */
    @Column(name = "keywords_start")
    private String keywordsStart;

    /**
     * 关键字结束
     */
    @Column(name = "keywords_end")
    private String keywordsEnd;

    /**
     * 描述开始
     */
    @Column(name = "description_start")
    private String descriptionStart;

    /**
     * 描述结束
     */
    @Column(name = "description_end")
    private String descriptionEnd;

    /**
     * 内容开始
     */
    @Column(name = "content_start")
    private String contentStart;

    /**
     * 内容结束
     */
    @Column(name = "content_end")
    private String contentEnd;

    /**
     * 内容分页开始
     */
    @Column(name = "pagination_start")
    private String paginationStart;

    /**
     * 内容分页结束
     */
    @Column(name = "pagination_end")
    private String paginationEnd;

    /**
     * 浏览量开始
     */
    @Column(name = "view_start")
    private String viewStart;

    /**
     * 浏览量结束
     */
    @Column(name = "view_end")
    private String viewEnd;

    /**
     * id前缀
     */
    @Column(name = "view_id_start")
    private String viewIdStart;

    /**
     * id后缀
     */
    @Column(name = "view_id_end")
    private String viewIdEnd;

    /**
     * 浏览量动态访问地址
     */
    @Column(name = "view_link")
    private String viewLink;

    /**
     * 发布时间开始
     */
    @Column(name = "release_time_start")
    private String releaseTimeStart;

    /**
     * 发布时间结束
     */
    @Column(name = "release_time_end")
    private String releaseTimeEnd;

    /**
     * 发布时间格式
     */
    @Column(name = "release_time_format")
    private String releaseTimeFormat;

    /**
     * 作者开始
     */
    @Column(name = "author_start")
    private String authorStart;

    /**
     * 作者结束
     */
    @Column(name = "author_end")
    private String authorEnd;

    /**
     * 来源开始
     */
    @Column(name = "origin_start")
    private String originStart;

    /**
     * 来源结束
     */
    @Column(name = "origin_end")
    private String originEnd;

    /**
     * 内容地址补全url
     */
    @Column(name = "content_prefix")
    private String contentPrefix;

    /**
     * 图片地址补全url
     */
    @Column(name = "img_prefix")
    private String imgPrefix;

    /**
     * 队列
     */
    @Column(name = "queue")
    private int queue;

    /**
     * 指定来源
     */
    @Column(name = "origin_appoint")
    private String originAppoint;

    /**
     * 是否默认类型图0：否1：是
     */
    @Column(name = "def_type_img")
    private boolean defTypeImg;

    /**
     * 类型图开始
     */
    @Column(name = "type_img_start")
    private String typeImgStart;

    /**
     * 类型图结束
     */
    @Column(name = "type_img_end")
    private String typeImgEnd;

    /**
     * 内容分页地址补全
     */
    @Column(name = "content_page_prefix")
    private String contentPagePrefix;

    /**
     * 内容分页开始
     */
    @Column(name = "content_page_start")
    private String contentPageStart;

    /**
     * 内容分页结束
     */
    @Column(name = "content_page_end")
    private String contentPageEnd;

    /**
     * 内容分页链接开始
     */
    @Column(name = "page_link_start")
    private String pageLinkStart;

    /**
     * 内容分页链接结束
     */
    @Column(name = "page_link_end")
    private String pageLinkEnd;

    /**
     * 创建者
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User creator;

    /**
     * 所属类容类型
     */
    @ManyToOne
    @JoinColumn(name = "type_id")
    @NotNull(message = "入库类型不能为空!")
    private ContentType type;

    /**
     * 所属栏目
     */
    @ManyToOne
    @JoinColumn(name = "channel_id")
    @NotNull(message = "入库栏目不能为空!")
    private Channel channel;

    /**
     * 采集的相关内容替换
     */
    @OneToMany(mappedBy = "acquisition", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<AcquisitionReplace> replaceWords;

    /**
     * 采集的相关内容屏蔽
     */
    @OneToMany(mappedBy = "acquisition", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<AcquisitionShield> shields;

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

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCurrNum() {
        return currNum;
    }

    public void setCurrNum(int currNum) {
        this.currNum = currNum;
    }

    public int getCurrItem() {
        return currItem;
    }

    public void setCurrItem(int currItem) {
        this.currItem = currItem;
    }

    public int getTotalItem() {
        return totalItem;
    }

    public void setTotalItem(int totalItem) {
        this.totalItem = totalItem;
    }

    public int getPauseTime() {
        return pauseTime;
    }

    public void setPauseTime(int pauseTime) {
        this.pauseTime = pauseTime;
    }

    public int getImgAcqu() {
        return imgAcqu;
    }

    public void setImgAcqu(int imgAcqu) {
        this.imgAcqu = imgAcqu;
    }

    public String getPageEncoding() {
        return pageEncoding;
    }

    public void setPageEncoding(String pageEncoding) {
        this.pageEncoding = pageEncoding;
    }

    public String getPlanList() {
        return planList;
    }

    public void setPlanList(String planList) {
        this.planList = planList;
    }

    public String getDynamicAddr() {
        return dynamicAddr;
    }

    public void setDynamicAddr(String dynamicAddr) {
        this.dynamicAddr = dynamicAddr;
    }

    public int getDynamicStart() {
        return dynamicStart;
    }

    public void setDynamicStart(int dynamicStart) {
        this.dynamicStart = dynamicStart;
    }

    public int getDynamicEnd() {
        return dynamicEnd;
    }

    public void setDynamicEnd(int dynamicEnd) {
        this.dynamicEnd = dynamicEnd;
    }

    public String getLinksetStart() {
        return linksetStart;
    }

    public void setLinksetStart(String linksetStart) {
        this.linksetStart = linksetStart;
    }

    public String getLinksetEnd() {
        return linksetEnd;
    }

    public void setLinksetEnd(String linksetEnd) {
        this.linksetEnd = linksetEnd;
    }

    public String getLinkStart() {
        return linkStart;
    }

    public void setLinkStart(String linkStart) {
        this.linkStart = linkStart;
    }

    public String getLinkEnd() {
        return linkEnd;
    }

    public void setLinkEnd(String linkEnd) {
        this.linkEnd = linkEnd;
    }

    public String getTitleStart() {
        return titleStart;
    }

    public void setTitleStart(String titleStart) {
        this.titleStart = titleStart;
    }

    public String getTitleEnd() {
        return titleEnd;
    }

    public void setTitleEnd(String titleEnd) {
        this.titleEnd = titleEnd;
    }

    public String getKeywordsStart() {
        return keywordsStart;
    }

    public void setKeywordsStart(String keywordsStart) {
        this.keywordsStart = keywordsStart;
    }

    public String getKeywordsEnd() {
        return keywordsEnd;
    }

    public void setKeywordsEnd(String keywordsEnd) {
        this.keywordsEnd = keywordsEnd;
    }

    public String getDescriptionStart() {
        return descriptionStart;
    }

    public void setDescriptionStart(String descriptionStart) {
        this.descriptionStart = descriptionStart;
    }

    public String getDescriptionEnd() {
        return descriptionEnd;
    }

    public void setDescriptionEnd(String descriptionEnd) {
        this.descriptionEnd = descriptionEnd;
    }

    public String getContentStart() {
        return contentStart;
    }

    public void setContentStart(String contentStart) {
        this.contentStart = contentStart;
    }

    public String getContentEnd() {
        return contentEnd;
    }

    public void setContentEnd(String contentEnd) {
        this.contentEnd = contentEnd;
    }

    public String getPaginationStart() {
        return paginationStart;
    }

    public void setPaginationStart(String paginationStart) {
        this.paginationStart = paginationStart;
    }

    public String getPaginationEnd() {
        return paginationEnd;
    }

    public void setPaginationEnd(String paginationEnd) {
        this.paginationEnd = paginationEnd;
    }

    public String getViewStart() {
        return viewStart;
    }

    public void setViewStart(String viewStart) {
        this.viewStart = viewStart;
    }

    public String getViewEnd() {
        return viewEnd;
    }

    public void setViewEnd(String viewEnd) {
        this.viewEnd = viewEnd;
    }

    public String getViewIdStart() {
        return viewIdStart;
    }

    public void setViewIdStart(String viewIdStart) {
        this.viewIdStart = viewIdStart;
    }

    public String getViewIdEnd() {
        return viewIdEnd;
    }

    public void setViewIdEnd(String viewIdEnd) {
        this.viewIdEnd = viewIdEnd;
    }

    public String getViewLink() {
        return viewLink;
    }

    public void setViewLink(String viewLink) {
        this.viewLink = viewLink;
    }

    public String getReleaseTimeStart() {
        return releaseTimeStart;
    }

    public void setReleaseTimeStart(String releaseTimeStart) {
        this.releaseTimeStart = releaseTimeStart;
    }

    public String getReleaseTimeEnd() {
        return releaseTimeEnd;
    }

    public void setReleaseTimeEnd(String releaseTimeEnd) {
        this.releaseTimeEnd = releaseTimeEnd;
    }

    public String getReleaseTimeFormat() {
        return releaseTimeFormat;
    }

    public void setReleaseTimeFormat(String releaseTimeFormat) {
        this.releaseTimeFormat = releaseTimeFormat;
    }

    public String getAuthorStart() {
        return authorStart;
    }

    public void setAuthorStart(String authorStart) {
        this.authorStart = authorStart;
    }

    public String getAuthorEnd() {
        return authorEnd;
    }

    public void setAuthorEnd(String authorEnd) {
        this.authorEnd = authorEnd;
    }

    public String getOriginStart() {
        return originStart;
    }

    public void setOriginStart(String originStart) {
        this.originStart = originStart;
    }

    public String getOriginEnd() {
        return originEnd;
    }

    public void setOriginEnd(String originEnd) {
        this.originEnd = originEnd;
    }

    public String getContentPrefix() {
        return contentPrefix;
    }

    public void setContentPrefix(String contentPrefix) {
        this.contentPrefix = contentPrefix;
    }

    public String getImgPrefix() {
        return imgPrefix;
    }

    public void setImgPrefix(String imgPrefix) {
        this.imgPrefix = imgPrefix;
    }

    public int getQueue() {
        return queue;
    }

    public void setQueue(int queue) {
        this.queue = queue;
    }

    public String getOriginAppoint() {
        return originAppoint;
    }

    public void setOriginAppoint(String originAppoint) {
        this.originAppoint = originAppoint;
    }

    public boolean isDefTypeImg() {
        return defTypeImg;
    }

    public void setDefTypeImg(boolean defTypeImg) {
        this.defTypeImg = defTypeImg;
    }

    public String getTypeImgStart() {
        return typeImgStart;
    }

    public void setTypeImgStart(String typeImgStart) {
        this.typeImgStart = typeImgStart;
    }

    public String getTypeImgEnd() {
        return typeImgEnd;
    }

    public void setTypeImgEnd(String typeImgEnd) {
        this.typeImgEnd = typeImgEnd;
    }

    public String getContentPagePrefix() {
        return contentPagePrefix;
    }

    public void setContentPagePrefix(String contentPagePrefix) {
        this.contentPagePrefix = contentPagePrefix;
    }

    public String getContentPageStart() {
        return contentPageStart;
    }

    public void setContentPageStart(String contentPageStart) {
        this.contentPageStart = contentPageStart;
    }

    public String getContentPageEnd() {
        return contentPageEnd;
    }

    public void setContentPageEnd(String contentPageEnd) {
        this.contentPageEnd = contentPageEnd;
    }

    public String getPageLinkStart() {
        return pageLinkStart;
    }

    public void setPageLinkStart(String pageLinkStart) {
        this.pageLinkStart = pageLinkStart;
    }

    public String getPageLinkEnd() {
        return pageLinkEnd;
    }

    public void setPageLinkEnd(String pageLinkEnd) {
        this.pageLinkEnd = pageLinkEnd;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public ContentType getType() {
        return type;
    }

    public void setType(ContentType type) {
        this.type = type;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public Set<AcquisitionReplace> getReplaceWords() {
        return replaceWords;
    }

    public void setReplaceWords(Set<AcquisitionReplace> replaceWords) {
        this.replaceWords = replaceWords;
    }

    public Set<AcquisitionShield> getShields() {
        return shields;
    }

    public void setShields(Set<AcquisitionShield> shields) {
        this.shields = shields;
    }
}