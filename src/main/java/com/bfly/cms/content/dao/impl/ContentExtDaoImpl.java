package com.bfly.cms.content.dao.impl;

import com.bfly.core.base.dao.impl.AbstractHibernateBaseDao;
import org.springframework.stereotype.Repository;

import com.bfly.cms.content.dao.ContentExtDao;
import com.bfly.cms.content.entity.ContentExt;

/**
 * 
 * @author andy_hulibo@163.com
 * @date 2018/11/26 15:41
 */
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