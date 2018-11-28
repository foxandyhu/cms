package com.bfly.cms.channel.service.impl;

import com.bfly.cms.channel.entity.Channel;
import com.bfly.cms.channel.service.ChannelExtMng;
import com.bfly.cms.channel.dao.ChannelExtDao;
import com.bfly.cms.channel.entity.ChannelExt;
import com.bfly.common.hibernate4.Updater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author andy_hulibo@163.com
 * @date 2018/11/26 14:32
 */
@Service
@Transactional(rollbackFor = Exception.class)
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