package com.bfly.cms.statistic.entity;

import com.bfly.common.util.DateUtils;
import com.bfly.cms.siteconfig.entity.CmsSite;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.json.JSONException;
import org.json.JSONObject;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 访问详细页面
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/17 10:06
 */
@Entity
@Table(name = "jc_site_access_pages")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class CmsSiteAccessPages implements Serializable {
    private static final long serialVersionUID = 1L;

    public JSONObject convertToJson()
            throws JSONException {
        JSONObject json = new JSONObject();
        if (getId() != null) {
            json.put("id", getId());
        } else {
            json.put("id", "");
        }
        if (StringUtils.isNotBlank(getAccessPage())) {
            json.put("accessPage", getAccessPage());
        } else {
            json.put("accessPage", "");
        }
        if (StringUtils.isNotBlank(getSessionId())) {
            json.put("sessionId", getSessionId());
        } else {
            json.put("sessionId", "");
        }
        if (getAccessTime() != null) {
            json.put("accessTime", DateUtils.parseToShortTimeStr(getAccessTime()));
        } else {
            json.put("accessTime", "");
        }
        if (getAccessDate() != null) {
            json.put("accessDate", DateUtils.parseDateToDateStr(getAccessDate()));
        } else {
            json.put("accessDate", "");
        }
        if (getVisitSecond() != null) {
            json.put("visitSecond", getVisitSecond());
        } else {
            json.put("visitSecond", "");
        }
        if (getPageIndex() != null) {
            json.put("pageIndex", getPageIndex());
        } else {
            json.put("pageIndex", "");
        }
        return json;
    }


    @Id
    @Column(name = "access_pages_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 访问页面
     */
    @Column(name = "access_page")
    private String accessPage;

    @Column(name = "session_id")
    private String sessionId;

    /**
     * 访问时间
     */
    @Column(name = "access_time")
    private Date accessTime;

    /**
     * 访问日期
     */
    @Column(name = "access_date")
    private Date accessDate;

    /**
     * 停留时长（秒）
     */
    @Column(name = "visit_second")
    private Integer visitSecond;

    /**
     * 用户访问页面的索引
     */
    @Column(name = "page_index")
    private Integer pageIndex;

    @ManyToOne
    @JoinColumn(name = "site_id")
    private CmsSite site;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccessPage() {
        return accessPage;
    }


    public void setAccessPage(String accessPage) {
        this.accessPage = accessPage;
    }


    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Date getAccessTime() {
        return accessTime;
    }


    public void setAccessTime(Date accessTime) {
        this.accessTime = accessTime;
    }

    public Date getAccessDate() {
        return accessDate;
    }

    public void setAccessDate(Date accessDate) {
        this.accessDate = accessDate;
    }


    public Integer getVisitSecond() {
        return visitSecond;
    }

    public void setVisitSecond(Integer visitSecond) {
        this.visitSecond = visitSecond;
    }


    public Integer getPageIndex() {
        return pageIndex;
    }


    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public CmsSite getSite() {
        return site;
    }

    public void setSite(CmsSite site) {
        this.site = site;
    }


}