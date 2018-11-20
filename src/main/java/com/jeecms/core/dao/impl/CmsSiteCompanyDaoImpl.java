package com.jeecms.core.dao.impl;

import org.springframework.stereotype.Repository;

import com.jeecms.common.hibernate4.AbstractHibernateBaseDao;
import com.jeecms.core.dao.CmsSiteCompanyDao;
import com.jeecms.core.entity.CmsSiteCompany;

@Repository
public class CmsSiteCompanyDaoImpl extends
        AbstractHibernateBaseDao<CmsSiteCompany, Integer> implements CmsSiteCompanyDao {

	@Override
    public CmsSiteCompany save(CmsSiteCompany bean) {
		getSession().save(bean);
		return bean;
	}

	@Override
	protected Class<CmsSiteCompany> getEntityClass() {
		return CmsSiteCompany.class;
	}
}