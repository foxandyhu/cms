package com.bfly.cms.user.entity;

import com.bfly.cms.siteconfig.entity.CmsSite;
import com.bfly.cms.user.entity.CmsUser;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.json.JSONObject;
import javax.persistence.*;
import java.io.Serializable;

/**
 * CMS管理员站点类
 * @author andy_hulibo@163.com
 * @date 2018/11/15 22:39
 */
@Table(name = "jc_user_site")
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE,region = "beanCache")
public class CmsUserSite implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "usersite_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/**
	 * 审核级别
	 */
	@Column(name = "check_step")
	private Byte checkStep;

	/**
	 * 是否拥有所有栏目的权限
	 */
	@Column(name = "is_all_channel")
	private Boolean allChannel;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private CmsUser user;

	@ManyToOne
	@JoinColumn(name = "site_id")
	private CmsSite site;

	public Integer getId () {
		return id;
	}


	public void setId (Integer id) {
		this.id = id;
	}


	public Byte getCheckStep () {
		return checkStep;
	}


	public void setCheckStep (Byte checkStep) {
		this.checkStep = checkStep;
	}


	public Boolean getAllChannel () {
		return allChannel;
	}

	
	public void setAllChannel (Boolean allChannel) {
		this.allChannel = allChannel;
	}


	public CmsUser getUser () {
		return user;
	}


	public void setUser (CmsUser user) {
		this.user = user;
	}


	
	public CmsSite getSite () {
		return site;
	}


	public void setSite (CmsSite site) {
		this.site = site;
	}

	public JSONObject convertToJson(){
		JSONObject json = new JSONObject();
		if (getId()!=null) {
			json.put("id", getId());
		}else{
			json.put("id", "");
		}
		if (getCheckStep()!=null) {
			json.put("checkStep", getCheckStep());
		}else{
			json.put("checkStep", "");
		}
		if (getAllChannel()!=null) {
			json.put("allChannel", getAllChannel());
		}else{
			json.put("allChannel", "");
		}
		return json;
	}
}