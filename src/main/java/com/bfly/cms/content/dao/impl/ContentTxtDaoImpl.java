package com.bfly.cms.content.dao.impl;

import org.springframework.stereotype.Repository;

import com.bfly.cms.content.dao.ContentTxtDao;
import com.bfly.cms.content.entity.ContentTxt;
import com.bfly.core.base.dao.impl.AbstractHibernateBaseDao;

@Repository
public class ContentTxtDaoImpl extends AbstractHibernateBaseDao<ContentTxt, Integer>
		implements ContentTxtDao {
	@Override
    public ContentTxt findById(Integer id) {
		ContentTxt entity = get(id);
		return entity;
	}

	@Override
    public ContentTxt save(ContentTxt bean) {
		getSession().save(bean);
		return bean;
	}

	@Override
	protected Class<ContentTxt> getEntityClass() {
		return ContentTxt.class;
	}
}