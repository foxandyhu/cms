package com.jeecms.core.dao.impl;

import java.util.List;

import com.jeecms.common.hibernate4.AbstractHibernateBaseDao;
import org.springframework.stereotype.Repository;

import com.jeecms.core.dao.ConfigDao;
import com.jeecms.core.entity.Config;

@Repository
public class ConfigDaoImpl extends AbstractHibernateBaseDao<Config, String> implements
		ConfigDao {
	@Override
    @SuppressWarnings("unchecked")
	public List<Config> getList() {
		String hql = "from Config";
		return find(hql);
	}

	@Override
    public Config findById(String id) {
		Config entity = get(id);
		return entity;
	}

	@Override
    public Config save(Config bean) {
		getSession().save(bean);
		return bean;
	}

	@Override
    public Config deleteById(String id) {
		Config entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}

	@Override
	protected Class<Config> getEntityClass() {
		return Config.class;
	}
}