package com.bfly.cms.channel.dao;

import com.bfly.cms.channel.entity.ChannelTxt;
import com.bfly.common.hibernate4.Updater;

public interface ChannelTxtDao {
    ChannelTxt findById(Integer id);

    ChannelTxt save(ChannelTxt bean);

    ChannelTxt updateByUpdater(Updater<ChannelTxt> updater);
}