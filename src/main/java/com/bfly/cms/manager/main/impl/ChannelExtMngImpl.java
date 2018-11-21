package com.bfly.cms.manager.main.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bfly.cms.dao.main.ChannelExtDao;
import com.bfly.cms.entity.main.Channel;
import com.bfly.cms.entity.main.ChannelExt;
import com.bfly.cms.manager.main.ChannelExtMng;
import com.bfly.common.hibernate4.Updater;

@Service
@Transactional
public class ChannelExtMngImpl implements ChannelExtMng {
	@Override
    public ChannelExt save(ChannelExt ext, Channel channel) {
		channel.setChannelExt(ext);
		ext.setChannel(channel);
		ext.init();
		dao.save(ext);
		return ext;
	}

	@Override
    public ChannelExt update(ChannelExt ext) {
		Updater<ChannelExt> updater = new Updater<ChannelExt>(ext);
		updater.include(ChannelExt.PROP_FINAL_STEP);
		updater.include(ChannelExt.PROP_AFTER_CHECK);
		ChannelExt entity = dao.updateByUpdater(updater);
		entity.blankToNull();
		return entity;
	}
	@Autowired
	private ChannelExtDao dao;
}