package com.bfly.core.dao.impl;

import com.bfly.common.hibernate4.AbstractHibernateBaseDao;
import org.springframework.stereotype.Repository;

import com.bfly.core.dao.CmsUserSiteDao;
import com.bfly.core.entity.CmsUserSite;

@Repository
public class CmsUserSiteDaoImpl extends AbstractHibernateBaseDao<CmsUserSite, Integer>
		implements CmsUserSiteDao {
	@Override
    public CmsUserSite findById(Integer id) {
		CmsUserSite entity = get(id);
		return entity;
	}

	@Override
    public CmsUserSite save(CmsUserSite bean) {
		getSession().save(bean);
		return bean;
	}

	@Override
    public int deleteBySiteId(Integer siteId) {
		String hql = "delete from CmsUserSite bean where bean.site.id=:siteId";
		return getSession().createQuery(hql).setParameter("siteId", siteId)
				.executeUpdate();
	}

	@Override
    public CmsUserSite deleteById(Integer id) {
		CmsUserSite entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}

	@Override
    public void delete(CmsUserSite entity) {
		getSession().delete(entity);
	}

	@Override
	protected Class<CmsUserSite> getEntityClass() {
		return CmsUserSite.class;
	}
}