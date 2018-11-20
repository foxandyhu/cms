package com.jeecms.core.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jeecms.common.hibernate4.AbstractHibernateBaseDao;
import com.jeecms.core.dao.FtpDao;
import com.jeecms.core.entity.Ftp;

@Repository
public class FtpDaoImpl extends AbstractHibernateBaseDao<Ftp, Integer> implements
		FtpDao {
	@Override
    @SuppressWarnings("unchecked")
	public List<Ftp> getList() {
		String hql = "from Ftp bean";
		return find(hql);
	}

	@Override
    public Ftp findById(Integer id) {
		Ftp entity = get(id);
		return entity;
	}

	@Override
    public Ftp save(Ftp bean) {
		getSession().save(bean);
		return bean;
	}

	@Override
    public Ftp deleteById(Integer id) {
		Ftp entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}

	@Override
	protected Class<Ftp> getEntityClass() {
		return Ftp.class;
	}
}