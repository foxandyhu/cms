package com.bfly.admin.member.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.bfly.cms.channel.service.ChannelMng;
import com.bfly.cms.user.service.CmsGroupMng;
import com.bfly.cms.logs.service.CmsLogMng;
import com.bfly.cms.user.service.CmsRoleMng;
import com.bfly.cms.siteconfig.service.CmsSiteMng;
import com.bfly.cms.user.service.CmsUserMng;
import com.bfly.core.security.CmsAuthorizingRealm;

/**
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/27 10:43
 */
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
	protected CmsAuthorizingRealm authorizingRealm;
}
