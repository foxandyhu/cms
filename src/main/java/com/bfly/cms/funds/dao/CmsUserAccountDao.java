package com.bfly.cms.funds.dao;

import java.util.Date;

import com.bfly.common.hibernate4.Updater;
import com.bfly.common.page.Pagination;
import com.bfly.cms.funds.entity.CmsUserAccount;

public interface CmsUserAccountDao {
	
	 Pagination getPage(String username,Date drawTimeBegin,Date drawTimeEnd,
			int orderBy,int pageNo,int pageSize);
	
	 CmsUserAccount findById(Integer id);

	 CmsUserAccount save(CmsUserAccount bean);

	 CmsUserAccount updateByUpdater(Updater<CmsUserAccount> updater);
}