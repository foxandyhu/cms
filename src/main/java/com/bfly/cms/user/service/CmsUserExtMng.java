package com.bfly.cms.user.service;

import com.bfly.cms.user.entity.CmsUser;
import com.bfly.cms.user.entity.CmsUserExt;

public interface CmsUserExtMng {
	 CmsUserExt findById(Integer userId);
	
	 CmsUserExt save(CmsUserExt ext, CmsUser user);

	 CmsUserExt update(CmsUserExt ext, CmsUser user);
	
	 void clearDayCount();

	 int countByPhone(String mobile);
}