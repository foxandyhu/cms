package com.jeecms.cms.entity.assist;

import com.jeecms.cms.entity.main.Channel;
import com.jeecms.cms.entity.main.ContentType;
import com.jeecms.common.util.DateUtils;
import com.jeecms.core.entity.CmsSite;
import com.jeecms.core.entity.CmsUser;
import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

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

    public static enum AcquisitionResultType {
        SUCCESS, TITLESTARTNOTFOUND, TITLEENDNOTFOUND, CONTENTSTARTNOTFOUND, CONTENTENDNOTFOUND, VIEWSTARTNOTFOUND, VIEWENDNOTFOUND, AUTHORSTARTNOTFOUND, AUTHORENDNOTFOUND, ORIGINSTARTNOTFOUND, ORIGINENDNOTFOUND, TYPEIMGSTARTNOTFOUND, TYPEIMGENDNOTFOUND, DESCRISTARTNOTFOUND, DESCRIENDNOTFOUND, RELEASESTARTNOTFOUND, RELEASEENDNOTFOUND, PAGESTARTNOTFOUND, PAGEENDNOTFOUND, VIEWIDSTARTNOTFOUND, VIEWIDENDNOTFOUND, TITLEEXIST, URLEXIST, UNKNOW
    }

    public static enum AcquisitionRepeatCheckType {
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

    // primary key
    private Integer id;

    // fields
    private String name;
    private Date startTime;
    private Date endTime;
    private Integer status;
    private Integer currNum;
    private Integer currItem;
    private Integer totalItem;
    private Integer pauseTime;
    private Boolean imgAcqu;
    private String pageEncoding;
    private String planList;
    private String dynamicAddr;
    private Integer dynamicStart;
    private Integer dynamicEnd;
    private String linksetStart;
    private String linksetEnd;
    private String linkStart;
    private String linkEnd;
    private String titleStart;
    private String titleEnd;
    private String keywordsStart;
    private String keywordsEnd;
    private String descriptionStart;
    private String descriptionEnd;
    private String contentStart;
    private String contentEnd;
    private String paginationStart;
    private String paginationEnd;
    private String viewStart;
    private String viewEnd;
    private String viewIdStart;
    private String viewIdEnd;
    private String viewLink;
    private String releaseTimeStart;
    private String releaseTimeEnd;
    private String releaseTimeFormat;
    private String authorStart;
    private String authorEnd;
    private String originStart;
    private String originEnd;
    private String contentPrefix;
    private String imgPrefix;
    private Integer queue;
    private String originAppoint;
    private Boolean defTypeImg;
    private String typeImgStart;
    private String typeImgEnd;
    private String contentPagePrefix;
    private String contentPageStart;
    private String contentPageEnd;
    private String pageLinkStart;
    private String pageLinkEnd;

    // many to one
    private CmsUser user;
    private ContentType type;
    private CmsSite site;
    private Channel channel;

    // ont to many
    private Set<CmsAcquisitionReplace> replaceWords;
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