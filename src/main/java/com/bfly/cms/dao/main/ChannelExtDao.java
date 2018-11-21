package com.bfly.cms.dao.main;

import com.bfly.cms.entity.main.ChannelExt;
import com.bfly.common.hibernate4.Updater;

public interface ChannelExtDao {
    ChannelExt save(ChannelExt bean);

    ChannelExt updateByUpdater(Updater<ChannelExt> updater);
}