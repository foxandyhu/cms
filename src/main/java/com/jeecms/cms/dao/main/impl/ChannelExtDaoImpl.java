package com.jeecms.cms.dao.main.impl;

import org.springframework.stereotype.Repository;

import com.jeecms.cms.dao.main.ChannelExtDao;
import com.jeecms.cms.entity.main.ChannelExt;
import com.jeecms.common.hibernate4.AbstractHibernateBaseDao;

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