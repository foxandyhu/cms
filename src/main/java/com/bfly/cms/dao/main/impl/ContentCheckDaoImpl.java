package com.bfly.cms.dao.main.impl;

import org.springframework.stereotype.Repository;

import com.bfly.cms.dao.main.ContentCheckDao;
import com.bfly.cms.entity.main.ContentCheck;
import com.bfly.common.hibernate4.AbstractHibernateBaseDao;

@Repository
public class ContentCheckDaoImpl extends AbstractHibernateBaseDao<ContentCheck, Long>
		implements ContentCheckDao {
	@Override
    public ContentCheck findById(Long id) {
		ContentCheck entity = get(id);
		return entity;
	}

	@Override
    public ContentCheck save(ContentCheck bean) {
		getSession().save(bean);
		return bean;
	}

	@Override
	protected Class<ContentCheck> getEntityClass() {
		return ContentCheck.class;
	}
}