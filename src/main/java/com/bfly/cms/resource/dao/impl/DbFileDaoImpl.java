package com.bfly.cms.resource.dao.impl;

import com.bfly.core.base.dao.impl.AbstractHibernateBaseDao;
import org.springframework.stereotype.Repository;

import com.bfly.cms.resource.dao.DbFileDao;
import com.bfly.cms.resource.entity.DbFile;

@Repository
public class DbFileDaoImpl extends AbstractHibernateBaseDao<DbFile, String> implements
		DbFileDao {
	@Override
    public DbFile findById(String id) {
		DbFile entity = get(id);
		return entity;
	}

	@Override
    public DbFile save(DbFile bean) {
		getSession().save(bean);
		return bean;
	}

	@Override
    public DbFile deleteById(String id) {
		DbFile entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}

	@Override
	protected Class<DbFile> getEntityClass() {
		return DbFile.class;
	}
}