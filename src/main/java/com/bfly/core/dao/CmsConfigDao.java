package com.bfly.core.dao;

import com.bfly.common.hibernate4.Updater;
import com.bfly.core.entity.CmsConfig;

public interface CmsConfigDao {
	public CmsConfig findById(Integer id);

	public CmsConfig updateByUpdater(Updater<CmsConfig> updater);
}