package com.jeecms.cms.dao.main.impl;

import org.springframework.stereotype.Repository;

import com.jeecms.cms.dao.main.ContentTxtDao;
import com.jeecms.cms.entity.main.ContentTxt;
import com.jeecms.common.hibernate4.AbstractHibernateBaseDao;

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