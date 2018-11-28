package com.bfly.cms.channel.dao;

import com.bfly.cms.channel.entity.ChannelExt;
import com.bfly.common.hibernate4.Updater;

public interface ChannelExtDao {
    ChannelExt save(ChannelExt bean);

    ChannelExt updateByUpdater(Updater<ChannelExt> updater);
}