package com.bfly.cms.job.service;

import com.bfly.cms.user.entity.CmsUser;
import com.bfly.cms.job.entity.CmsUserResume;

public interface CmsUserResumeMng {
	 CmsUserResume save(CmsUserResume ext, CmsUser user);

	 CmsUserResume update(CmsUserResume ext, CmsUser user);
}