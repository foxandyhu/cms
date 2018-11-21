package com.bfly.core.dao.impl;

import org.springframework.stereotype.Repository;

import com.bfly.common.hibernate4.AbstractHibernateBaseDao;
import com.bfly.core.dao.CmsConfigDao;
import com.bfly.core.entity.CmsConfig;

@Repository
public class CmsConfigDaoImpl extends AbstractHibernateBaseDao<CmsConfig, Integer>
		implements CmsConfigDao {
	@Override
    public CmsConfig findById(Integer id) {
		CmsConfig entity = get(id);
		return entity;
	}

	@Override
	protected Class<CmsConfig> getEntityClass() {
		return CmsConfig.class;
	}
}