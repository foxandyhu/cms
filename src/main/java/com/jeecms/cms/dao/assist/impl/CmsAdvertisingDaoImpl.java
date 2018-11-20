package com.jeecms.cms.dao.assist.impl;

import java.util.List;

import com.jeecms.common.hibernate4.AbstractHibernateBaseDao;
import org.springframework.stereotype.Repository;

import com.jeecms.cms.dao.assist.CmsAdvertisingDao;
import com.jeecms.cms.entity.assist.CmsAdvertising;
import com.jeecms.common.hibernate4.Finder;
import com.jeecms.common.page.Pagination;

@Repository
public class CmsAdvertisingDaoImpl extends
        AbstractHibernateBaseDao<CmsAdvertising, Integer> implements CmsAdvertisingDao {
	@Override
    public Pagination getPage(Integer siteId, Integer adspaceId,
                              Boolean enabled, int pageNo, int pageSize) {
		Finder f = Finder.create("from CmsAdvertising bean where 1=1");
		if (siteId != null && adspaceId == null) {
			f.append(" and bean.site.id=:siteId");
			f.setParam("siteId", siteId);
		} else if (adspaceId != null) {
			f.append(" and bean.adspace.id=:adspaceId");
			f.setParam("adspaceId", adspaceId);
		}
		if (enabled != null) {
			f.append(" and bean.enabled=:enabled");
			f.setParam("enabled", enabled);
		}
		f.append(" order by bean.id desc");
		return find(f, pageNo, pageSize);
	}

	@Override
    @SuppressWarnings("unchecked")
	public List<CmsAdvertising> getList(Integer adspaceId, Boolean enabled) {
		Finder f = Finder.create("from CmsAdvertising bean where 1=1");
		if (adspaceId != null) {
			f.append(" and bean.adspace.id=:adspaceId");
			f.setParam("adspaceId", adspaceId);
		}
		if (enabled != null) {
			f.append(" and bean.enabled=:enabled");
			f.setParam("enabled", enabled);
		}
		return find(f);
	}

	@Override
    public CmsAdvertising findById(Integer id) {
		CmsAdvertising entity = get(id);
		return entity;
	}

	@Override
    public CmsAdvertising save(CmsAdvertising bean) {
		getSession().save(bean);
		return bean;
	}

	@Override
    public CmsAdvertising deleteById(Integer id) {
		CmsAdvertising entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}

	@Override
	protected Class<CmsAdvertising> getEntityClass() {
		return CmsAdvertising.class;
	}
}