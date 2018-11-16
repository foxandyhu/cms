package com.jeecms.cms.entity.assist;

import com.jeecms.cms.entity.assist.base.BaseCmsSiteAccess;
import com.jeecms.core.entity.CmsSite;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Date;


public class CmsSiteAccess implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static final String ENGINE_BAIDU=".baidu.";
	public static final String ENGINE_GOOGLE=".google.";
	public static final String ENGINE_YAHOO=".yahoo.";
	public static final String ENGINE_BING=".bing.";
	public static final String ENGINE_SOGOU=".sogou.";
	public static final String ENGINE_SOSO=".soso.";
	public static final String ENGINE_SO=".so.";

	// primary key
	private Integer id;

	// fields
	private String sessionId;
	private Date accessTime;
	private Date accessDate;
	private String ip;
	private String area;
	private String accessSource;
	private String externalLink;
	private String engine;
	private String entryPage;
	private String lastStopPage;
	private Integer visitSecond;
	private Integer visitPageCount;
	private String operatingSystem;
	private String browser;
	private String keyword;

	@ManyToOne
	@JoinColumn(name = "site_id")
	private CmsSite site;


	public Integer getId () {
		return id;
	}

	
	public void setId (Integer id) {
		this.id = id;
	}

	public String getSessionId () {
		return sessionId;
	}

	public void setSessionId (String sessionId) {
		this.sessionId = sessionId;
	}


	public Date getAccessTime () {
		return accessTime;
	}


	public void setAccessTime (Date accessTime) {
		this.accessTime = accessTime;
	}

	public Date getAccessDate () {
		return accessDate;
	}

	public void setAccessDate (Date accessDate) {
		this.accessDate = accessDate;
	}

	public String getIp () {
		return ip;
	}

	public void setIp (String ip) {
		this.ip = ip;
	}


	public String getArea () {
		return area;
	}

	public void setArea (String area) {
		this.area = area;
	}

	public String getAccessSource () {
		return accessSource;
	}

	
	public void setAccessSource (String accessSource) {
		this.accessSource = accessSource;
	}


	public String getExternalLink () {
		return externalLink;
	}

	
	public void setExternalLink (String externalLink) {
		this.externalLink = externalLink;
	}


	public String getEngine () {
		return engine;
	}

	public void setEngine (String engine) {
		this.engine = engine;
	}

	public String getEntryPage () {
		return entryPage;
	}

	public void setEntryPage (String entryPage) {
		this.entryPage = entryPage;
	}


	public String getLastStopPage () {
		return lastStopPage;
	}

	public void setLastStopPage (String lastStopPage) {
		this.lastStopPage = lastStopPage;
	}

	public Integer getVisitSecond () {
		return visitSecond;
	}

	public void setVisitSecond (Integer visitSecond) {
		this.visitSecond = visitSecond;
	}

	public Integer getVisitPageCount () {
		return visitPageCount;
	}


	public void setVisitPageCount (Integer visitPageCount) {
		this.visitPageCount = visitPageCount;
	}

	public String getOperatingSystem () {
		return operatingSystem;
	}

	public void setOperatingSystem (String operatingSystem) {
		this.operatingSystem = operatingSystem;
	}

	public String getBrowser () {
		return browser;
	}


	public void setBrowser (String browser) {
		this.browser = browser;
	}

	public String getKeyword () {
		return keyword;
	}


	public void setKeyword (String keyword) {
		this.keyword = keyword;
	}


	public CmsSite getSite () {
		return site;
	}


	public void setSite (CmsSite site) {
		this.site = site;
	}


}