package com.bfly.plug.store.dao.impl;

import com.bfly.common.hibernate4.AbstractHibernateBaseDao;
import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import com.bfly.common.page.Pagination;
import com.bfly.plug.store.dao.PlugStoreConfigDao;
import com.bfly.plug.store.entity.PlugStoreConfig;

@Repository
public class PlugStoreConfigDaoImpl extends AbstractHibernateBaseDao<PlugStoreConfig, Integer> implements PlugStoreConfigDao {
	@Override
    public Pagination getPage(int pageNo, int pageSize) {
		Criteria crit = createCriteria();
		Pagination page = findByCriteria(crit, pageNo, pageSize);
		return page;
	}

	@Override
    public PlugStoreConfig findById(Integer id) {
		PlugStoreConfig entity = get(id);
		return entity;
	}

	@Override
    public PlugStoreConfig save(PlugStoreConfig bean) {
		getSession().save(bean);
		return bean;
	}

	@Override
    public PlugStoreConfig deleteById(Integer id) {
		PlugStoreConfig entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}
	
	@Override
	protected Class<PlugStoreConfig> getEntityClass() {
		return PlugStoreConfig.class;
	}
}