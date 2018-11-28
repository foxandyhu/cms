package com.bfly.cms.user.dao;

import java.util.List;

import com.bfly.common.hibernate4.Updater;
import com.bfly.cms.user.entity.CmsGroup;

public interface CmsGroupDao {
	 List<CmsGroup> getList();

	 CmsGroup getRegDef();

	 CmsGroup findById(Integer id);
	
	 CmsGroup findByName(String name);

	 CmsGroup save(CmsGroup bean);

	 CmsGroup updateByUpdater(Updater<CmsGroup> updater);

	 CmsGroup deleteById(Integer id);
}