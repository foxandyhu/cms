package com.bfly.cms.content.dao.impl;

import org.springframework.stereotype.Repository;

import com.bfly.cms.content.dao.ContentCheckDao;
import com.bfly.cms.content.entity.ContentCheck;
import com.bfly.core.base.dao.impl.AbstractHibernateBaseDao;

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