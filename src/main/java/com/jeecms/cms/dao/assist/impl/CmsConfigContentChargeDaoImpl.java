package com.jeecms.cms.dao.assist.impl;

import com.jeecms.common.hibernate4.AbstractHibernateBaseDao;
import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import com.jeecms.common.page.Pagination;
import com.jeecms.cms.dao.assist.CmsConfigContentChargeDao;
import com.jeecms.cms.entity.assist.CmsConfigContentCharge;

@Repository
public class CmsConfigContentChargeDaoImpl extends AbstractHibernateBaseDao<CmsConfigContentCharge, Integer> implements CmsConfigContentChargeDao {
	@Override
    public Pagination getPage(int pageNo, int pageSize) {
		Criteria crit = createCriteria();
		Pagination page = findByCriteria(crit, pageNo, pageSize);
		return page;
	}

	@Override
    public CmsConfigContentCharge findById(Integer id) {
		CmsConfigContentCharge entity = get(id);
		return entity;
	}

	@Override
    public CmsConfigContentCharge save(CmsConfigContentCharge bean) {
		getSession().save(bean);
		return bean;
	}

	@Override
    public CmsConfigContentCharge deleteById(Integer id) {
		CmsConfigContentCharge entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}
	
	@Override
	protected Class<CmsConfigContentCharge> getEntityClass() {
		return CmsConfigContentCharge.class;
	}
}