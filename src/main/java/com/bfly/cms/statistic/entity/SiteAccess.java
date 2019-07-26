package com.bfly.cms.statistic.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 站点访问Session 记录
 * 每个客户访问站点时记录该次会话的基本情况,每一次新的session会话
 * 系统会自动记录一条记录
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/17 9:56
 */
@Entity
@Table(name = "site_access")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class SiteAccess implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * 会话ID
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/23 17:04
     */
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
     * 访问IP
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/23 17:05
     */
    @Column(name = "access_ip")
    private String accessIp;

    /**
     * 访问国家
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/23 18:23
     */
    @Column(name = "access_country")
    private String accessCountry;

    /**
     * 访问地区
     */
    @Column(name = "access_area")
    private String accessArea;

    /**
     * 访问来源
     */
    @Column(name = "access_source")
    private String accessSource;

    /**
     * 入口页面
     */
    @Column(name = "entry_page")
    private String entryPage;

    /**
     * 最后停留页面
     */
    @Column(name = "stop_page")
    private String stopPage;

    /**
     * 访问时长(秒)
     */
    @Column(name = "visit_second")
    private long visitSecond;

    /**
     * 访问页面数
     */
    @Column(name = "visit_page_count")
    private int visitPageCount;

    /**
     * 操作系统
     */
    @Column(name = "os_name")
    private String osName;

    /**
     * 设备类型,手机,PC等
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/23 21:10
     */
    @Column(name = "os_type")
    private String osType;

    /**
     * 浏览器
     */
    @Column(name = "browser_name")
    private String browserName;

    /**
     * 浏览器版本
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/23 21:09
     */
    @Column(name = "browser_version")
    private String browserVersion;

    /**
     * 来源来访值,当来源是搜索引擎时 value值为搜索引擎名称,外部链接时为链接地址
     */
    @Column(name = "access_value")
    private String accessValue;

    public String getOsName() {
        return osName;
    }

    public void setOsName(String osName) {
        this.osName = osName;
    }

    public String getOsType() {
        return osType;
    }

    public void setOsType(String osType) {
        this.osType = osType;
    }

    public String getBrowserVersion() {
        return browserVersion;
    }

    public void setBrowserVersion(String browserVersion) {
        this.browserVersion = browserVersion;
    }

    public String getAccessValue() {
        return accessValue;
    }

    public void setAccessValue(String accessValue) {
        this.accessValue = accessValue;
    }

    public String getAccessCountry() {
        return accessCountry;
    }

    public void setAccessCountry(String accessCountry) {
        this.accessCountry = accessCountry;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getAccessIp() {
        return accessIp;
    }

    public void setAccessIp(String accessIp) {
        this.accessIp = accessIp;
    }

    public String getAccessArea() {
        return accessArea;
    }

    public void setAccessArea(String accessArea) {
        this.accessArea = accessArea;
    }

    public String getAccessSource() {
        return accessSource;
    }

    public void setAccessSource(String accessSource) {
        this.accessSource = accessSource;
    }

    public String getEntryPage() {
        return entryPage;
    }

    public void setEntryPage(String entryPage) {
        this.entryPage = entryPage;
    }

    public String getStopPage() {
        return stopPage;
    }

    public void setStopPage(String stopPage) {
        this.stopPage = stopPage;
    }

    public long getVisitSecond() {
        return visitSecond;
    }

    public void setVisitSecond(long visitSecond) {
        this.visitSecond = visitSecond;
    }

    public int getVisitPageCount() {
        return visitPageCount;
    }

    public void setVisitPageCount(int visitPageCount) {
        this.visitPageCount = visitPageCount;
    }

    public String getBrowserName() {
        return browserName;
    }

    public void setBrowserName(String browserName) {
        this.browserName = browserName;
    }
}