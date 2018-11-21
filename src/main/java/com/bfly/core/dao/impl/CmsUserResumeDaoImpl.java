package com.bfly.core.dao.impl;

import com.bfly.common.hibernate4.AbstractHibernateBaseDao;
import org.springframework.stereotype.Repository;

import com.bfly.core.dao.CmsUserResumeDao;
import com.bfly.core.entity.CmsUserResume;

@Repository
public class CmsUserResumeDaoImpl extends AbstractHibernateBaseDao<CmsUserResume, Integer> implements CmsUserResumeDao {
	@Override
    public CmsUserResume findById(Integer id) {
		CmsUserResume entity = get(id);
		return entity;
	}

	@Override
    public CmsUserResume save(CmsUserResume bean) {
		getSession().save(bean);
		return bean;
	}
	
	@Override
	protected Class<CmsUserResume> getEntityClass() {
		return CmsUserResume.class;
	}
}