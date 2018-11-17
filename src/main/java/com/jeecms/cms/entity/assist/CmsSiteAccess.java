package com.jeecms.cms.entity.assist;

import com.jeecms.core.entity.CmsSite;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 站点访问
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/17 9:56
 */
@Entity
@Table(name = "jc_site_access")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class CmsSiteAccess implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final String ENGINE_BAIDU = ".baidu.";
    public static final String ENGINE_GOOGLE = ".google.";
    public static final String ENGINE_YAHOO = ".yahoo.";
    public static final String ENGINE_BING = ".bing.";
    public static final String ENGINE_SOGOU = ".sogou.";
    public static final String ENGINE_SOSO = ".soso.";
    public static final String ENGINE_SO = ".so.";

    @Id
    @Column(name = "access_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

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

    @Column(name = "ip")
    private String ip;

    /**
     * 访问地区
     */
    @Column(name = "area")
    private String area;

    /**
     * 访问来源
     */
    @Column(name = "access_source")
    private String accessSource;

    /**
     * 外部链接网址
     */
    @Column(name = "external_link")
    private String externalLink;

    /**
     * 搜索引擎
     */
    @Column(name = "engine")
    private String engine;

    /**
     * 入口页面
     */
    @Column(name = "entry_page")
    private String entryPage;

    /**
     * 最后停留页面
     */
    @Column(name = "last_stop_page")
    private String lastStopPage;

    /**
     * 访问时长(秒)
     */
    @Column(name = "visit_second")
    private Integer visitSecond;

    /**
     * 访问页面数
     */
    @Column(name = "visit_page_count")
    private Integer visitPageCount;

    /**
     * 操作系统
     */
    @Column(name = "operating_system")
    private String operatingSystem;

    /**
     * 浏览器
     */
    @Column(name = "browser")
    private String browser;

    /**
     * 来访关键字
     */
    @Column(name = "keyword")
    private String keyword;

    @ManyToOne
    @JoinColumn(name = "site_id")
    private CmsSite site;


    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
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

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }


    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAccessSource() {
        return accessSource;
    }


    public void setAccessSource(String accessSource) {
        this.accessSource = accessSource;
    }


    public String getExternalLink() {
        return externalLink;
    }


    public void setExternalLink(String externalLink) {
        this.externalLink = externalLink;
    }


    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public String getEntryPage() {
        return entryPage;
    }

    public void setEntryPage(String entryPage) {
        this.entryPage = entryPage;
    }


    public String getLastStopPage() {
        return lastStopPage;
    }

    public void setLastStopPage(String lastStopPage) {
        this.lastStopPage = lastStopPage;
    }

    public Integer getVisitSecond() {
        return visitSecond;
    }

    public void setVisitSecond(Integer visitSecond) {
        this.visitSecond = visitSecond;
    }

    public Integer getVisitPageCount() {
        return visitPageCount;
    }


    public void setVisitPageCount(Integer visitPageCount) {
        this.visitPageCount = visitPageCount;
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    public String getBrowser() {
        return browser;
    }


    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getKeyword() {
        return keyword;
    }


    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }


    public CmsSite getSite() {
        return site;
    }


    public void setSite(CmsSite site) {
        this.site = site;
    }


}