package com.bfly.cms.dao.main.impl;

import com.bfly.common.hibernate4.AbstractHibernateBaseDao;
import org.springframework.stereotype.Repository;

import com.bfly.cms.dao.main.ChannelTxtDao;
import com.bfly.cms.entity.main.ChannelTxt;

@Repository
public class ChannelTxtDaoImpl extends AbstractHibernateBaseDao<ChannelTxt, Integer>
		implements ChannelTxtDao {
	@Override
    public ChannelTxt findById(Integer id) {
		ChannelTxt entity = get(id);
		return entity;
	}

	@Override
    public ChannelTxt save(ChannelTxt bean) {
		getSession().save(bean);
		return bean;
	}

	@Override
	protected Class<ChannelTxt> getEntityClass() {
		return ChannelTxt.class;
	}
}