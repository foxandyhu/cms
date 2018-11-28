package com.bfly.cms.user.dao.impl;

import com.bfly.core.base.dao.impl.AbstractHibernateBaseDao;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.bfly.cms.user.dao.CmsUserExtDao;
import com.bfly.cms.user.entity.CmsUserExt;

@Repository
public class CmsUserExtDaoImpl extends AbstractHibernateBaseDao<CmsUserExt, Integer> implements CmsUserExtDao {
	@Override
    public CmsUserExt findById(Integer id) {
		CmsUserExt entity = get(id);
		return entity;
	}

	@Override
    public CmsUserExt save(CmsUserExt bean) {
		getSession().save(bean);
		return bean;
	}
	
	@Override
	protected Class<CmsUserExt> getEntityClass() {
		return CmsUserExt.class;
	}
	
	@Override
    public void clearDayCount(){
		String hql="update CmsUserExt ext set ext.todayGuestbookTotal=0,ext.todayCommentTotal=0";
		getSession().createQuery(hql).executeUpdate();
	}
	@Override
	public int countByPhone(String mobile) {
		String hql = "select count(*) from CmsUserExt bean where bean.mobile = :mobile";
		Query query = getSession().createQuery(hql);
		query.setParameter("mobile", mobile);
		return ((Number)query.iterate().next()).intValue();
	}
}