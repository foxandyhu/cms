package com.bfly.cms.resource.dao;

import com.bfly.common.hibernate4.Updater;
import com.bfly.cms.resource.entity.DbFile;

public interface DbFileDao {
	 DbFile findById(String id);

	 DbFile save(DbFile bean);

	 DbFile updateByUpdater(Updater<DbFile> updater);

	 DbFile deleteById(String id);
}