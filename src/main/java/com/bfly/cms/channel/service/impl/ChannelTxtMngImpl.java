package com.bfly.cms.channel.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bfly.cms.channel.dao.ChannelTxtDao;
import com.bfly.cms.channel.entity.Channel;
import com.bfly.cms.channel.entity.ChannelTxt;
import com.bfly.cms.channel.service.ChannelTxtMng;
import com.bfly.common.hibernate4.Updater;

/**
 * @author andy_hulibo@163.com
 * @date 2018/11/26 14:31
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ChannelTxtMngImpl implements ChannelTxtMng {

    @Override
    public ChannelTxt save(ChannelTxt txt, Channel channel) {
        if (txt.isAllBlank()) {
            return null;
        } else {
            txt.setChannel(channel);
            txt.init();
            dao.save(txt);
            return txt;
        }
    }


    @Override
    public ChannelTxt update(ChannelTxt txt, Channel channel) {
        ChannelTxt entity = dao.findById(channel.getId());
        if (entity == null) {
            entity = save(txt, channel);
            channel.setChannelTxt(entity);
            return entity;
        } else {
            if (txt.isAllBlank()) {
                channel.setChannelTxt(null);
                return null;
            } else {
                Updater<ChannelTxt> updater = new Updater<ChannelTxt>(txt);
                entity = dao.updateByUpdater(updater);
                entity.blankToNull();
                return entity;
            }
        }
    }

    @Autowired
    private ChannelTxtDao dao;
}