package com.bfly.cms.dao.main.impl;

import org.springframework.stereotype.Repository;

import com.bfly.cms.dao.main.ChannelExtDao;
import com.bfly.cms.entity.main.ChannelExt;
import com.bfly.common.hibernate4.AbstractHibernateBaseDao;

@Repository
public class ChannelExtDaoImpl extends AbstractHibernateBaseDao<ChannelExt, Integer>
		implements ChannelExtDao {
	@Override
    public ChannelExt save(ChannelExt bean) {
		getSession().save(bean);
		return bean;
	}

	@Override
	protected Class<ChannelExt> getEntityClass() {
		return ChannelExt.class;
	}
}