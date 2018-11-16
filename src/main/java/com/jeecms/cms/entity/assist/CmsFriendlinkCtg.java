package com.jeecms.cms.entity.assist;

import com.jeecms.core.entity.CmsSite;
import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.jeecms.cms.entity.assist.base.BaseCmsFriendlinkCtg;

import java.io.Serializable;


public class CmsFriendlinkCtg  implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public JSONObject convertToJson() 
			throws JSONException{
		JSONObject json=new JSONObject();
		if (getId()!=null) {
			json.put("id", getId());
		}else{
			json.put("id", "");
		}
		if (getPriority()!=null) {
			json.put("priority", getPriority());
		}else{
			json.put("priority", "");
		}
		if (StringUtils.isNotBlank(getName())) {
			json.put("name", getName());
		}else{
			json.put("name", "");
		}
		return json;
	}
	
	public void init(){
		if (getPriority()==null) {
			setPriority(10);
		}
	}

	// primary key
	private Integer id;

	// fields
	private String name;
	private Integer priority;

	// many to one
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


	public Integer getPriority () {
		return priority;
	}


	public void setPriority (Integer priority) {
		this.priority = priority;
	}

	public CmsSite getSite () {
		return site;
	}


	public void setSite (CmsSite site) {
		this.site = site;
	}


}