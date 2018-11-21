package com.bfly.cms.dao.assist.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bfly.cms.dao.assist.CmsAdvertisingSpaceDao;
import com.bfly.cms.entity.assist.CmsAdvertisingSpace;
import com.bfly.common.hibernate4.Finder;
import com.bfly.common.hibernate4.AbstractHibernateBaseDao;

@Repository
public class CmsAdvertisingSpaceDaoImpl extends
        AbstractHibernateBaseDao<CmsAdvertisingSpace, Integer> implements
		CmsAdvertisingSpaceDao {
	@Override
    @SuppressWarnings("unchecked")
	public List<CmsAdvertisingSpace> getList(Integer siteId) {
		Finder f = Finder.create("from CmsAdvertisingSpace bean");
		if (siteId != null) {
			f.append(" where bean.site.id=:siteId");
			f.setParam("siteId", siteId);
		}
		return find(f);
	}

	@Override
    public CmsAdvertisingSpace findById(Integer id) {
		CmsAdvertisingSpace entity = get(id);
		return entity;
	}

	@Override
    public CmsAdvertisingSpace save(CmsAdvertisingSpace bean) {
		getSession().save(bean);
		return bean;
	}

	@Override
    public CmsAdvertisingSpace deleteById(Integer id) {
		CmsAdvertisingSpace entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}

	@Override
	protected Class<CmsAdvertisingSpace> getEntityClass() {
		return CmsAdvertisingSpace.class;
	}
}