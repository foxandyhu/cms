package com.bfly.cms.system.dao.impl;

import com.bfly.core.base.dao.impl.AbstractHibernateBaseDao;
import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import com.bfly.common.page.Pagination;
import com.bfly.cms.system.dao.CmsConfigContentChargeDao;
import com.bfly.cms.system.entity.CmsConfigContentCharge;

/**
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/24 18:24
 */
@Repository
public class CmsConfigContentChargeDaoImpl extends AbstractHibernateBaseDao<CmsConfigContentCharge, Integer> implements CmsConfigContentChargeDao {

	@Override
    public Pagination getPage(int pageNo, int pageSize) {
		Criteria c = createCriteria();
		return findByCriteria(c, pageNo, pageSize);
	}

	@Override
    public CmsConfigContentCharge findById(Integer id) {
		return get(id);
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