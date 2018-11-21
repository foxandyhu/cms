package com.bfly.core.dao;

import com.bfly.common.hibernate4.Updater;
import com.bfly.core.entity.CmsUserExt;

public interface CmsUserExtDao {
	public CmsUserExt findById(Integer id);

	public CmsUserExt save(CmsUserExt bean);

	public CmsUserExt updateByUpdater(Updater<CmsUserExt> updater);
	
	public void clearDayCount();

	public int countByPhone(String mobile);
}