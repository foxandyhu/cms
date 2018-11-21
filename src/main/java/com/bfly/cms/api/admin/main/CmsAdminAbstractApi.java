package com.bfly.cms.api.admin.main;

import org.springframework.beans.factory.annotation.Autowired;

import com.bfly.cms.manager.assist.CmsWebserviceMng;
import com.bfly.cms.manager.main.ChannelMng;
import com.bfly.core.manager.CmsGroupMng;
import com.bfly.core.manager.CmsLogMng;
import com.bfly.core.manager.CmsRoleMng;
import com.bfly.core.manager.CmsSiteMng;
import com.bfly.core.manager.CmsUserMng;
import com.bfly.core.security.CmsAuthorizingRealm;

public class CmsAdminAbstractApi {
	@Autowired
	protected CmsSiteMng cmsSiteMng;
	@Autowired
	protected ChannelMng channelMng;
	@Autowired
	protected CmsRoleMng cmsRoleMng;
	@Autowired
	protected CmsGroupMng cmsGroupMng;
	@Autowired
	protected CmsLogMng cmsLogMng;
	@Autowired
	protected CmsUserMng manager;
	@Autowired
	protected CmsWebserviceMng cmsWebserviceMng;
	@Autowired
	protected CmsAuthorizingRealm authorizingRealm;
}
