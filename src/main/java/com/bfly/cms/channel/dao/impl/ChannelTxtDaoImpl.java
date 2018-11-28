package com.bfly.cms.channel.dao.impl;

import com.bfly.cms.channel.dao.ChannelTxtDao;
import com.bfly.cms.channel.entity.ChannelTxt;
import com.bfly.core.base.dao.impl.AbstractHibernateBaseDao;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author andy_hulibo@163.com
 * @date 2018/11/26 14:32
 */
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