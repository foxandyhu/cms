package com.bfly.core.manager;

import com.bfly.core.entity.CmsUser;
import com.bfly.core.entity.CmsUserExt;

public interface CmsUserExtMng {
	public CmsUserExt findById(Integer userId);
	
	public CmsUserExt save(CmsUserExt ext, CmsUser user);

	public CmsUserExt update(CmsUserExt ext, CmsUser user);
	
	public void clearDayCount();

	public int countByPhone(String mobile);
}