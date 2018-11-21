package com.bfly.core.dao.impl;

import java.util.List;

import com.bfly.common.hibernate4.AbstractHibernateBaseDao;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.bfly.core.dao.CmsSiteDao;
import com.bfly.core.entity.CmsSite;

@Repository
public class CmsSiteDaoImpl extends AbstractHibernateBaseDao<CmsSite, Integer>
		implements CmsSiteDao {

	@Override
    public int siteCount(boolean cacheable) {
		String hql = "select count(*) from CmsSite bean";
		return ((Number) getSession().createQuery(hql).setCacheable(cacheable)
				.iterate().next()).intValue();
	}
	

	@Override
    @SuppressWarnings("unchecked")
	public List<CmsSite> getList(boolean cacheable) {
		String hql = "from CmsSite bean order by bean.id asc";
		return getSession().createQuery(hql).setCacheable(cacheable).list();
	}
	
	@Override
    public int getCountByProperty(String property){
		String hql = "select count(distinct "+property+") from CmsSite bean ";
		Query query = getSession().createQuery(hql);
		return ((Number) query.iterate().next()).intValue();
	}

	@Override
    public CmsSite findByDomain(String domain) {
		return findUniqueByProperty("domain",domain);
	}

	@Override
    public CmsSite findById(Integer id) {
		CmsSite entity = get(id);
		return entity;
	}

	@Override
    public CmsSite save(CmsSite bean) {
		getSession().save(bean);
		return bean;
	}

	@Override
    public CmsSite deleteById(Integer id) {
		CmsSite entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}

	public CmsSite getByDomain(String domain) {
		String hql = "from CmsSite bean where bean.domain=?";
		return findUniqueByProperty(hql, domain);
	}

	@Override
	protected Class<CmsSite> getEntityClass() {
		return CmsSite.class;
	}
}