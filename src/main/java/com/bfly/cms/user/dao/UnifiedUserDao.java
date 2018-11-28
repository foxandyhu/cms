package com.bfly.cms.user.dao;

import java.util.List;

import com.bfly.common.hibernate4.Updater;
import com.bfly.common.page.Pagination;
import com.bfly.cms.user.entity.UnifiedUser;

public interface UnifiedUserDao {
	 UnifiedUser getByUsername(String username);

	 List<UnifiedUser> getByEmail(String email);

	 int countByEmail(String email);

	 Pagination getPage(int pageNo, int pageSize);

	 UnifiedUser findById(Integer id);

	 UnifiedUser save(UnifiedUser bean);

	 UnifiedUser updateByUpdater(Updater<UnifiedUser> updater);

	 UnifiedUser deleteById(Integer id);
}