package com.bfly.cms.job.dao.impl;

import com.bfly.core.base.dao.impl.AbstractHibernateBaseDao;
import org.springframework.stereotype.Repository;

import com.bfly.cms.job.dao.CmsUserResumeDao;
import com.bfly.cms.job.entity.CmsUserResume;

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