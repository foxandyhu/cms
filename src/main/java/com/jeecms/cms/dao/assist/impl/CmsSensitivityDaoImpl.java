package com.jeecms.cms.dao.assist.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jeecms.cms.dao.assist.CmsSensitivityDao;
import com.jeecms.cms.entity.assist.CmsSensitivity;
import com.jeecms.common.hibernate4.AbstractHibernateBaseDao;

@Repository
public class CmsSensitivityDaoImpl extends
        AbstractHibernateBaseDao<CmsSensitivity, Integer> implements CmsSensitivityDao {
	@Override
    @SuppressWarnings("unchecked")
	public List<CmsSensitivity> getList(boolean cacheable) {
		String hql = "from CmsSensitivity bean order by bean.id desc";
		return getSession().createQuery(hql).setCacheable(cacheable).list();
	}

	@Override
    public CmsSensitivity findById(Integer id) {
		CmsSensitivity entity = get(id);
		return entity;
	}

	@Override
    public CmsSensitivity save(CmsSensitivity bean) {
		getSession().save(bean);
		return bean;
	}

	@Override
    public CmsSensitivity deleteById(Integer id) {
		CmsSensitivity entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}

	@Override
	protected Class<CmsSensitivity> getEntityClass() {
		return CmsSensitivity.class;
	}
}