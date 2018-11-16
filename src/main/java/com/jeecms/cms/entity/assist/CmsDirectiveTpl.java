package com.jeecms.cms.entity.assist;

import com.jeecms.core.entity.CmsUser;
import org.apache.commons.lang.StringUtils;

import com.jeecms.cms.entity.assist.base.BaseCmsDirectiveTpl;

import net.sf.json.JSONObject;

import java.io.Serializable;


public class CmsDirectiveTpl implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public JSONObject convertToJson(){
		JSONObject json = new JSONObject();
		if (getId()!=null) {
			json.put("id", getId());
		}else{
			json.put("id", "");
		}
		if (StringUtils.isNotBlank(getName())) {
			json.put("name", getName());
		}else{
			json.put("name", "");
		}
		if (StringUtils.isNotBlank(getDescription())) {
			json.put("description", getDescription());
		}else{
			json.put("description", "");
		}
		if (StringUtils.isNotBlank(getCode())) {
			json.put("code", getCode());
		}else{
			json.put("code", "");
		}
		if (getUser()!=null&&StringUtils.isNotBlank(getUser().getUsername())) {
			json.put("username", getUser().getUsername());
		}else{
			json.put("username", "");
		}
		
		return json;
	}

	// primary key
	private Integer id;

	// fields
	private String name;
	private String description;
	private String code;

	// many to one
	private CmsUser user;


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


	public String getDescription () {
		return description;
	}


	public void setDescription (String description) {
		this.description = description;
	}


	public String getCode () {
		return code;
	}

	public void setCode (String code) {
		this.code = code;
	}


	public CmsUser getUser () {
		return user;
	}

	public void setUser (CmsUser user) {
		this.user = user;
	}

}