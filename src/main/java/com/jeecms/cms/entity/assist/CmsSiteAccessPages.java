package com.jeecms.cms.entity.assist;

import com.jeecms.core.entity.CmsSite;
import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.jeecms.cms.entity.assist.base.BaseCmsSiteAccessPages;
import com.jeecms.common.util.DateUtils;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Date;


public class CmsSiteAccessPages implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public JSONObject convertToJson() 
			throws JSONException{
		JSONObject json=new JSONObject();
		if (getId()!=null) {
			json.put("id", getId());
		}else{
			json.put("id", "");
		}
		if (StringUtils.isNotBlank(getAccessPage())) {
			json.put("accessPage", getAccessPage());
		}else{
			json.put("accessPage", "");
		}
		if (StringUtils.isNotBlank(getSessionId())) {
			json.put("sessionId", getSessionId());
		}else{
			json.put("sessionId", "");
		}
		if(getAccessTime()!=null){
			json.put("accessTime", DateUtils.parseToShortTimeStr(getAccessTime()));
		}else{
			json.put("accessTime", "");
		}
		if(getAccessDate()!=null){
			json.put("accessDate", DateUtils.parseDateToDateStr(getAccessDate()));
		}else{
			json.put("accessDate", "");
		}
		if(getVisitSecond()!=null){
			json.put("visitSecond",getVisitSecond());
		}else{
			json.put("visitSecond", "");
		}
		if(getPageIndex()!=null){
			json.put("pageIndex",getPageIndex());
		}else{
			json.put("pageIndex", "");
		}
		return json;
	}

	// primary key
	private Integer id;

	// fields
	private String accessPage;
	private String sessionId;
	private Date accessTime;
	private Date accessDate;
	private Integer visitSecond;
	private Integer pageIndex;

	@ManyToOne
	@JoinColumn(name = "site_id")
	private CmsSite site;


	public Integer getId () {
		return id;
	}

	public void setId (Integer id) {
		this.id = id;
	}

	public String getAccessPage () {
		return accessPage;
	}


	public void setAccessPage (String accessPage) {
		this.accessPage = accessPage;
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


	public Integer getVisitSecond () {
		return visitSecond;
	}

	public void setVisitSecond (Integer visitSecond) {
		this.visitSecond = visitSecond;
	}


	public Integer getPageIndex () {
		return pageIndex;
	}


	public void setPageIndex (Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

	public CmsSite getSite () {
		return site;
	}

	public void setSite (CmsSite site) {
		this.site = site;
	}



}