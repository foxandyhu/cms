package com.bfly.cms.user.service;

import com.bfly.cms.siteconfig.entity.CmsSite;
import com.bfly.cms.user.entity.CmsUser;
import com.bfly.cms.user.entity.CmsUserSite;

public interface CmsUserSiteMng {
	 CmsUserSite findById(Integer id);

	 CmsUserSite save(CmsSite site, CmsUser user, Byte step,
			Boolean allChannel);

	 CmsUserSite update(CmsUserSite bean);

	 void updateByUser(CmsUser user, Integer siteId, Byte step,
			Boolean allChannel);

	 void updateByUser(CmsUser user, Integer[] siteIds, Byte[] steps,
			Boolean[] allChannels);

	 int deleteBySiteId(Integer siteId);

	 CmsUserSite deleteById(Integer id);

	 CmsUserSite[] deleteByIds(Integer[] ids);
}