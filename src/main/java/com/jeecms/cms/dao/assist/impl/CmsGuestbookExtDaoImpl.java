package com.jeecms.cms.dao.assist.impl;

import com.jeecms.common.hibernate4.AbstractHibernateBaseDao;
import org.springframework.stereotype.Repository;

import com.jeecms.cms.dao.assist.CmsGuestbookExtDao;
import com.jeecms.cms.entity.assist.CmsGuestbookExt;

@Repository
public class CmsGuestbookExtDaoImpl extends
        AbstractHibernateBaseDao<CmsGuestbookExt, Integer> implements
		CmsGuestbookExtDao {

	@Override
    public CmsGuestbookExt findById(Integer id) {
		CmsGuestbookExt entity = get(id);
		return entity;
	}

	@Override
    public CmsGuestbookExt save(CmsGuestbookExt bean) {
		getSession().save(bean);
		return bean;
	}

	@Override
    public CmsGuestbookExt deleteById(Integer id) {
		CmsGuestbookExt entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}

	@Override
	protected Class<CmsGuestbookExt> getEntityClass() {
		return CmsGuestbookExt.class;
	}
}