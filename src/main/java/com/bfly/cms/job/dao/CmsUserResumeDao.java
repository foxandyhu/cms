package com.bfly.cms.job.dao;

import com.bfly.common.hibernate4.Updater;
import com.bfly.cms.job.entity.CmsUserResume;

public interface CmsUserResumeDao {
	 CmsUserResume findById(Integer id);

	 CmsUserResume save(CmsUserResume bean);

	 CmsUserResume updateByUpdater(Updater<CmsUserResume> updater);
}