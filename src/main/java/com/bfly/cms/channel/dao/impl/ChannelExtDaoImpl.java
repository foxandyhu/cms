package com.bfly.cms.channel.dao.impl;

import org.springframework.stereotype.Repository;

import com.bfly.cms.channel.dao.ChannelExtDao;
import com.bfly.cms.channel.entity.ChannelExt;
import com.bfly.core.base.dao.impl.AbstractHibernateBaseDao;

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