package com.bfly.cms.acquisition.dao.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.bfly.cms.acquisition.dao.CmsAcquisitionReplaceDao;
import com.bfly.cms.acquisition.entity.CmsAcquisitionReplace;
import com.bfly.core.base.dao.impl.Finder;
import com.bfly.core.base.dao.impl.AbstractHibernateBaseDao;
@Component
public class CmsAcquisitionReplaceDaoImpl extends AbstractHibernateBaseDao<CmsAcquisitionReplace, Integer> implements CmsAcquisitionReplaceDao {

	@Override
	public CmsAcquisitionReplace save(CmsAcquisitionReplace bean) {
		getSession().save(bean);
		return bean;
	}

	@Override
	public List<CmsAcquisitionReplace> getList(Integer acquisitionId) {
		Finder f=Finder.create("from CmsAcquisitionReplace bean where 1 = 1");
		if (acquisitionId!=null) {
			f.append(" and bean.acquisition.id = :acquisitionId");
			f.setParam("acquisitionId", acquisitionId);
		}
		return find(f);
	}

	@Override
	protected Class<CmsAcquisitionReplace> getEntityClass() {
		return CmsAcquisitionReplace.class;
	}
}
