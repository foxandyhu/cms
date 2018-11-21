package com.bfly.core.manager;

import com.bfly.core.entity.CmsUser;
import com.bfly.core.entity.CmsUserResume;

public interface CmsUserResumeMng {
	public CmsUserResume save(CmsUserResume ext, CmsUser user);

	public CmsUserResume update(CmsUserResume ext, CmsUser user);
}