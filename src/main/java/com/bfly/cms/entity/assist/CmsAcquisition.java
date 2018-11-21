package com.bfly.cms.entity.assist;

import com.bfly.cms.entity.main.Channel;
import com.bfly.cms.entity.main.ContentType;
import com.bfly.common.util.DateUtils;
import com.bfly.core.entity.CmsSite;
import com.bfly.core.entity.CmsUser;
import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * CMS采集类
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/16 16:14
 */
@Entity
@Table(name = "jc_acquisition")
public class CmsAcquisition implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 动态页翻页页号
     */
    public static final String PAGE = "[page]";
    /**
     * 停止状态
     */
    public static final int STOP = 0;
    /**
     * 采集状态
     */
    public static final int START = 1;
    /**
     * 暂停状态
     */
    public static final int PAUSE = 2;

    public enum AcquisitionResultType {
        SUCCESS, TITLESTARTNOTFOUND, TITLEENDNOTFOUND, CONTENTSTARTNOTFOUND, CONTENTENDNOTFOUND, VIEWSTARTNOTFOUND, VIEWENDNOTFOUND, AUTHORSTARTNOTFOUND, AUTHORENDNOTFOUND, ORIGINSTARTNOTFOUND, ORIGINENDNOTFOUND, TYPEIMGSTARTNOTFOUND, TYPEIMGENDNOTFOUND, DESCRISTARTNOTFOUND, DESCRIENDNOTFOUND, RELEASESTARTNOTFOUND, RELEASEENDNOTFOUND, PAGESTARTNOTFOUND, PAGEENDNOTFOUND, VIEWIDSTARTNOTFOUND, VIEWIDENDNOTFOUND, TITLEEXIST, URLEXIST, UNKNOW
    }

    public enum AcquisitionRepeatCheckType {
        NONE, TITLE, URL
    }

    public JSONObject convertToJson() {
        JSONObject json = new JSONObject();
        if (getId() != null) {
            json.put("id", getId());
        } else {
            json.put("id", "");
        }
        if (StringUtils.isNotBlank(getName())) {
            json.put("name", getName());
        } else {
            json.put("name", "");
        }
        if (getStartTime() != null) {
            json.put("startTime", DateUtils.parseDateToTimeStr(getStartTime()));
        } else {
            json.put("startTime", "");
        }
        if (getEndTime() != null) {
            json.put("endTime", DateUtils.parseDateToTimeStr(getEndTime()));
        } else {
            json.put("endTime", "");
        }
        if (getStatus() != null) {
            json.put("status", getStatus());
        } else {
            json.put("status", "");
        }
        if (getCurrNum() != null) {
            json.put("currNum", getCurrNum());
        } else {
            json.put("currNum", "");
        }
        if (getCurrItem() != null) {
            json.put("currItem", getCurrItem());
        } else {
            json.put("currItem", "");
        }
        if (getTotalItem() != null) {
            json.put("totalItem", getTotalItem());
        } else {
            json.put("totalItem", "");
        }
        if (getPauseTime() != null) {
            json.put("pauseTime", getPauseTime());
        } else {
            json.put("pauseTime", "");
        }
        if (getImgAcqu() != null) {
            json.put("imgAcqu", getImgAcqu());
        } else {
            json.put("imgAcqu", "");
        }
        if (StringUtils.isNotBlank(getPageEncoding())) {
            json.put("pageEncoding", getPageEncoding());
        } else {
            json.put("pageEncoding", "");
        }
        if (StringUtils.isNotBlank(getPlanList())) {
            json.put("planList", getPlanList());
        } else {
            json.put("planList", "");
        }
        if (StringUtils.isNotBlank(getDynamicAddr())) {
            json.put("dynamicAddr", getDynamicAddr());
        } else {
            json.put("dynamicAddr", "");
        }
        if (getDynamicStart() != null) {
            json.put("dynamicStart", getDynamicStart());
        } else {
            json.put("dynamicStart", "");
        }
        if (getDynamicEnd() != null) {
            json.put("dynamicEnd", getDynamicEnd());
        } else {
            json.put("dynamicEnd", "");
        }
        if (StringUtils.isNotBlank(getLinksetStart())) {
            json.put("linksetStart", getLinksetStart());
        } else {
            json.put("linksetStart", "");
        }
        if (StringUtils.isNotBlank(getLinksetEnd())) {
            json.put("linksetEnd", getLinksetEnd());
        } else {
            json.put("linksetEnd", "");
        }
        if (StringUtils.isNotBlank(getLinkStart())) {
            json.put("linkStart", getLinkStart());
        } else {
            json.put("linkStart", "");
        }
        if (StringUtils.isNotBlank(getLinkEnd())) {
            json.put("linkEnd", getLinkEnd());
        } else {
            json.put("linkEnd", "");
        }
        if (StringUtils.isNotBlank(getTitleStart())) {
            json.put("titleStart", getTitleStart());
        } else {
            json.put("titleStart", "");
        }
        if (StringUtils.isNotBlank(getTitleEnd())) {
            json.put("titleEnd", getTitleEnd());
        } else {
            json.put("titleEnd", "");
        }
        if (StringUtils.isNotBlank(getKeywordsStart())) {
            json.put("keywordsStart", getKeywordsStart());
        } else {
            json.put("keywordsStart", "");
        }
        if (StringUtils.isNotBlank(getKeywordsEnd())) {
            json.put("keywordsEnd", getKeywordsEnd());
        } else {
            json.put("keywordsEnd", "");
        }
        if (StringUtils.isNotBlank(getDescriptionStart())) {
            json.put("descriptionStart", getDescriptionStart());
        } else {
            json.put("descriptionStart", "");
        }
        if (StringUtils.isNotBlank(getDescriptionEnd())) {
            json.put("descriptionEnd", getDescriptionEnd());
        } else {
            json.put("descriptionEnd", "");
        }
        if (StringUtils.isNotBlank(getContentStart())) {
            json.put("contentStart", getContentStart());
        } else {
            json.put("contentStart", "");
        }
        if (StringUtils.isNotBlank(getContentEnd())) {
            json.put("contentEnd", getContentEnd());
        } else {
            json.put("contentEnd", "");
        }
        if (StringUtils.isNotBlank(getPaginationStart())) {
            json.put("paginationStart", getPaginationStart());
        } else {
            json.put("paginationStart", "");
        }
        if (StringUtils.isNotBlank(getPaginationEnd())) {
            json.put("paginationEnd", getPaginationEnd());
        } else {
            json.put("paginationEnd", "");
        }
        if (StringUtils.isNotBlank(getViewStart())) {
            json.put("viewStart", getViewStart());
        } else {
            json.put("viewStart", "");
        }
        if (StringUtils.isNotBlank(getViewEnd())) {
            json.put("viewEnd", getViewEnd());
        } else {
            json.put("viewEnd", "");
        }
        if (StringUtils.isNotBlank(getViewIdStart())) {
            json.put("viewIdStart", getViewIdStart());
        } else {
            json.put("viewIdStart", "");
        }
        if (StringUtils.isNotBlank(getViewIdEnd())) {
            json.put("viewIdEnd", getViewIdEnd());
        } else {
            json.put("viewIdEnd", "");
        }
        if (StringUtils.isNotBlank(getViewLink())) {
            json.put("viewLink", getViewLink());
        } else {
            json.put("viewLink", "");
        }
        if (StringUtils.isNotBlank(getReleaseTimeStart())) {
            json.put("releaseTimeStart", getReleaseTimeStart());
        } else {
            json.put("releaseTimeStart", "");
        }
        if (StringUtils.isNotBlank(getReleaseTimeEnd())) {
            json.put("releaseTimeEnd", getReleaseTimeEnd());
        } else {
            json.put("releaseTimeEnd", "");
        }
        if (StringUtils.isNotBlank(getReleaseTimeFormat())) {
            json.put("releaseTimeFormat", getReleaseTimeFormat());
        } else {
            json.put("releaseTimeFormat", "");
        }
        if (StringUtils.isNotBlank(getAuthorStart())) {
            json.put("authorStart", getAuthorStart());
        } else {
            json.put("authorStart", "");
        }
        if (StringUtils.isNotBlank(getAuthorEnd())) {
            json.put("authorEnd", getAuthorEnd());
        } else {
            json.put("authorEnd", "");
        }
        if (StringUtils.isNotBlank(getOriginStart())) {
            json.put("originStart", getOriginStart());
        } else {
            json.put("originStart", "");
        }
        if (StringUtils.isNotBlank(getOriginEnd())) {
            json.put("originEnd", getOriginEnd());
        } else {
            json.put("originEnd", "");
        }
        if (StringUtils.isNotBlank(getContentPrefix())) {
            json.put("contentPrefix", getContentPrefix());
        } else {
            json.put("contentPrefix", "");
        }
        if (StringUtils.isNotBlank(getImgPrefix())) {
            json.put("imgPrefix", getImgPrefix());
        } else {
            json.put("imgPrefix", "");
        }
        if (getQueue() != null) {
            json.put("queue", getQueue());
        } else {
            json.put("queue", "");
        }
        if (StringUtils.isNotBlank(getOriginAppoint())) {
            json.put("originAppoint", getOriginAppoint());
        } else {
            json.put("originAppoint", "");
        }
        if (getType() != null && getType().getId() != null) {
            json.put("typeId", getType().getId());
        } else {
            json.put("typeId", "");
        }
        if (getChannel() != null && getChannel().getId() != null) {
            json.put("channelId", getChannel().getId());
        } else {
            json.put("channelId", "");
        }
        if (getDefTypeImg() != null) {
            json.put("defTypeImg", getDefTypeImg());
        } else {
            json.put("defTypeImg", true);
        }
        if (StringUtils.isNotBlank(getTypeImgStart())) {
            json.put("typeImgStart", getTypeImgStart());
        } else {
            json.put("typeImgStart", "");
        }
        if (StringUtils.isNotBlank(getTypeImgEnd())) {
            json.put("typeImgEnd", getTypeImgEnd());
        } else {
            json.put("typeImgEnd", "");
        }
        if (StringUtils.isNotBlank(getContentPagePrefix())) {
            json.put("contentPagePrefix", getContentPagePrefix());
        } else {
            json.put("contentPagePrefix", "");
        }
        if (StringUtils.isNotBlank(getContentPageStart())) {
            json.put("contentPageStart", getContentPageStart());
        } else {
            json.put("contentPageStart", "");
        }
        if (StringUtils.isNotBlank(getContentPageEnd())) {
            json.put("contentPageEnd", getContentPageEnd());
        } else {
            json.put("contentPageEnd", "");
        }
        if (StringUtils.isNotBlank(getPageLinkStart())) {
            json.put("pageLinkStart", getPageLinkStart());
        } else {
            json.put("pageLinkStart", "");
        }
        if (StringUtils.isNotBlank(getPageLinkEnd())) {
            json.put("pageLinkEnd", getPageLinkEnd());
        } else {
            json.put("pageLinkEnd", "");
        }
        return json;
    }

    /**
     * 是否停止
     *
     * @return
     */
    public boolean isStop() {
        int status = getStatus();
        return status == 0;
    }

    /**
     * 是否暂停
     *
     * @return
     */
    public boolean isPuase() {
        int status = getStatus();
        return status == 2;
    }

    /**
     * 是否中断。停止和暂停都需要中断。
     *
     * @return
     */
    public boolean isBreak() {
        int status = getStatus();
        return status == 0 || status == 2;
    }

    public String[] getPlans() {
        String plan = getPlanList();
        if (!StringUtils.isBlank(plan)) {
            return StringUtils.split(plan);
        } else {
            return new String[0];
        }
    }

    public String[] getAllPlans() {
        String[] plans = getPlans();
        Integer start = getDynamicStart();
        Integer end = getDynamicEnd();
        if (!StringUtils.isBlank(getDynamicAddr()) && start != null
                && end != null && start >= 0 && end >= start) {
            int plansLen = plans.length;
            String[] allPlans = new String[plansLen + end - start + 1];
            for (int i = 0; i < plansLen; i++) {
                allPlans[i] = plans[i];
            }
            for (int i = 0, len = end - start + 1; i < len; i++) {
                allPlans[plansLen + i] = getDynamicAddrByNum(start + i);
            }
            return allPlans;
        } else {
            return plans;
        }
    }

    public String getDynamicAddrByNum(int num) {
        return StringUtils.replace(getDynamicAddr(), PAGE, String.valueOf(num));
    }

    public int getTotalNum() {
        int count = 0;
        Integer start = getDynamicStart();
        Integer end = getDynamicEnd();
        if (!StringUtils.isBlank(getDynamicAddr()) && start != null
                && end != null) {
            count = end - start + 1;
        }
        if (!StringUtils.isBlank(getPlanList())) {
            count += getPlans().length;
        }
        return count;
    }

    public void init() {
        if (getStatus() == null) {
            setStatus(STOP);
        }
        if (getCurrNum() == null) {
            setCurrNum(0);
        }
        if (getCurrItem() == null) {
            setCurrItem(0);
        }
        if (getTotalItem() == null) {
            setTotalItem(0);
        }
        if (getPauseTime() == null) {
            setPauseTime(0);
        }
        if (getQueue() == null) {
            setQueue(0);
        }
        if (getDefTypeImg() == null) {
            setDefTypeImg(true);
        }
    }

    @Id
    @Column(name = "acquisition_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 采集名称
     */
    @Column(name = "acq_name")
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
    private Integer status;

    /**
     * 当前号码
     */
    @Column(name = "curr_num")
    private Integer currNum;

    /**
     * 当前条数
     */
    @Column(name = "curr_item")
    private Integer currItem;

    /**
     * 每页总条数
     */
    @Column(name = "total_item")
    private Integer totalItem;

    /**
     * 暂停时间(毫秒)
     */
    @Column(name = "pause_time")
    private Integer pauseTime;

    /**
     * 是否采集图片
     */
    @Column(name = "img_acqu")
    private Boolean imgAcqu;

    /**
     * 页面编码
     */
    @Column(name = "page_encoding")
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
    private Integer dynamicStart;

    /**
     * 页码结束
     */
    @Column(name = "dynamic_end")
    private Integer dynamicEnd;

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
    private Integer queue;

    /**
     * 指定来源
     */
    @Column(name = "origin_appoint")
    private String originAppoint;

    /**
     * 是否默认类型图0：否1：是
     */
    @Column(name = "def_type_img")
    private Boolean defTypeImg;

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

    @ManyToOne
    @JoinColumn(name = "user_id")
    private CmsUser user;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private ContentType type;

    @ManyToOne
    @JoinColumn(name = "site_id")
    private CmsSite site;

    @ManyToOne
    @JoinColumn(name = "channel_id")
    private Channel channel;

    @OneToMany(mappedBy = "acquisition",cascade = CascadeType.REMOVE,orphanRemoval = true)
    private Set<CmsAcquisitionReplace> replaceWords;

    @OneToMany(mappedBy = "acquisition",cascade = CascadeType.REMOVE,orphanRemoval = true)
    private Set<CmsAcquisitionShield> shields;


    public Set<CmsAcquisitionShield> getShields() {
        return shields;
    }

    public void setShields(Set<CmsAcquisitionShield> shields) {
        this.shields = shields;
    }

    public Set<CmsAcquisitionReplace> getReplaceWords() {
        return replaceWords;
    }

    public void setReplaceWords(Set<CmsAcquisitionReplace> replaceWords) {
        this.replaceWords = replaceWords;
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

    public Boolean getDefTypeImg() {
        return defTypeImg;
    }

    public void setDefTypeImg(Boolean defTypeImg) {
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCurrNum() {
        return currNum;
    }

    public void setCurrNum(Integer currNum) {
        this.currNum = currNum;
    }

    public Integer getCurrItem() {
        return currItem;
    }

    public void setCurrItem(Integer currItem) {
        this.currItem = currItem;
    }

    public Integer getTotalItem() {
        return totalItem;
    }

    public void setTotalItem(Integer totalItem) {
        this.totalItem = totalItem;
    }

    public Integer getPauseTime() {
        return pauseTime;
    }

    public void setPauseTime(Integer pauseTime) {
        this.pauseTime = pauseTime;
    }

    public Boolean getImgAcqu() {
        return imgAcqu;
    }

    public void setImgAcqu(Boolean imgAcqu) {
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

    public Integer getDynamicStart() {
        return dynamicStart;
    }


    public void setDynamicStart(Integer dynamicStart) {
        this.dynamicStart = dynamicStart;
    }

    public Integer getDynamicEnd() {
        return dynamicEnd;
    }


    public void setDynamicEnd(Integer dynamicEnd) {
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

    public Integer getQueue() {
        return queue;
    }


    public void setQueue(Integer queue) {
        this.queue = queue;
    }


    public String getOriginAppoint() {
        return originAppoint;
    }

    public void setOriginAppoint(String originAppoint) {
        this.originAppoint = originAppoint;
    }

    public CmsUser getUser() {
        return user;
    }


    public void setUser(CmsUser user) {
        this.user = user;
    }


    public ContentType getType() {
        return type;
    }


    public void setType(ContentType type) {
        this.type = type;
    }


    public CmsSite getSite() {
        return site;
    }


    public void setSite(CmsSite site) {
        this.site = site;
    }

    public Channel getChannel() {
        return channel;
    }


    public void setChannel(Channel channel) {
        this.channel = channel;
    }


}