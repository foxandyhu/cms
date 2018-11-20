package com.jeecms.core.dao.impl;

import java.util.Date;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import com.jeecms.common.hibernate4.AbstractHibernateBaseDao;
import com.jeecms.common.page.Pagination;
import com.jeecms.core.dao.AuthenticationDao;
import com.jeecms.core.entity.Authentication;

@Repository
public class AuthenticationDaoImpl extends
        AbstractHibernateBaseDao<Authentication, String> implements AuthenticationDao {
	@Override
    public int deleteExpire(Date d) {
		String hql = "delete Authentication bean where bean.updateTime <= :d";
		return getSession().createQuery(hql).setTimestamp("d", d)
				.executeUpdate();
	}

	@Override
    public Authentication getByUserId(Long userId) {
		String hql = "from Authentication bean where bean.uid=?";
		return (Authentication) findUnique(hql, userId);
	}

	@Override
    public Pagination getPage(int pageNo, int pageSize) {
		Criteria crit = createCriteria();
		Pagination page = findByCriteria(crit, pageNo, pageSize);
		return page;
	}

	@Override
    public Authentication findById(String id) {
		Authentication entity = get(id);
		return entity;
	}

	@Override
    public Authentication save(Authentication bean) {
		getSession().save(bean);
		return bean;
	}

	@Override
    public Authentication deleteById(String id) {
		Authentication entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}

	@Override
	protected Class<Authentication> getEntityClass() {
		return Authentication.class;
	}
}