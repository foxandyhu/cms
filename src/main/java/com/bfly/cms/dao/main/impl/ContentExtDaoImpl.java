package com.bfly.cms.dao.main.impl;

import com.bfly.common.hibernate4.AbstractHibernateBaseDao;
import org.springframework.stereotype.Repository;

import com.bfly.cms.dao.main.ContentExtDao;
import com.bfly.cms.entity.main.ContentExt;

@Repository
public class ContentExtDaoImpl extends AbstractHibernateBaseDao<ContentExt, Integer>
		implements ContentExtDao {
	@Override
    public ContentExt findById(Integer id) {
		ContentExt entity = get(id);
		return entity;
	}

	@Override
    public ContentExt save(ContentExt bean) {
		getSession().save(bean);
		return bean;
	}

	@Override
	protected Class<ContentExt> getEntityClass() {
		return ContentExt.class;
	}
}