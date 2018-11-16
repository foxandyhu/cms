package com.jeecms.cms.entity.assist;

import com.jeecms.core.entity.CmsSite;
import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.jeecms.cms.entity.assist.base.BaseCmsSearchWords;

import java.io.Serializable;


public class CmsSearchWords implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 搜索次数降序
	 */
	public static final int HIT_DESC=1;
	/**
	 * 优先级降序
	 */
	public static final int PRIORITY_DESC=3;
	/**
	 *  
	 */
	public static final int HIT_ASC=2;
	/**
	 * 优先级升序
	 */
	public static final int PRIORITY_ASC=4;
	
	public static final int DEFAULT_PRIORITY=10;
	
	public JSONObject convertToJson() 
			throws JSONException{
		JSONObject json=new JSONObject();
		if (getId()!=null) {
			json.put("id", getId());
		}else{
			json.put("id", "");
		}
		if (getHitCount()!=null) {
			json.put("hitCount", getHitCount());
		}else{
			json.put("hitCount", "");
		}
		if (StringUtils.isNotBlank(getName())) {
			json.put("name", getName());
		}else{
			json.put("name", "");
		}
		if (getPriority()!=null) {
			json.put("priority", getPriority());
		}else{
			json.put("priority", "");
		}
		if (getRecommend()!=null) {
			json.put("recommend", getRecommend());
		}else{
			json.put("recommend", "");
		}
		return json;
	}
	
	public void init(){
		if (getPriority()==null) {
			setPriority(10);
		}
		if (getRecommend()==null) {
			setRecommend(false);
		}
	}

	// primary key
	private Integer id;

	// fields
	private String name;
	private Integer hitCount;
	private Integer priority;
	private String nameInitial;
	private Boolean recommend;
	private CmsSite site;

	public Integer getId () {
		return id;
	}

	public void setId (Integer id) {
		this.id = id;
	}

	public String getName () {
		return name;
	}

	public void setName (String name) {
		this.name = name;
	}

	public Integer getHitCount () {
		return hitCount;
	}

	public void setHitCount (Integer hitCount) {
		this.hitCount = hitCount;
	}

	public Integer getPriority () {
		return priority;
	}

	public void setPriority (Integer priority) {
		this.priority = priority;
	}

	public String getNameInitial () {
		return nameInitial;
	}

	public void setNameInitial (String nameInitial) {
		this.nameInitial = nameInitial;
	}

	public Boolean getRecommend() {
		return recommend;
	}

	public void setRecommend(Boolean recommend) {
		this.recommend = recommend;
	}

	public CmsSite getSite() {
		return site;
	}

	public void setSite(CmsSite site) {
		this.site = site;
	}

}