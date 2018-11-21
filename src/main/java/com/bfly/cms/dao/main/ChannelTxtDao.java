package com.bfly.cms.dao.main;

import com.bfly.cms.entity.main.ChannelTxt;
import com.bfly.common.hibernate4.Updater;

public interface ChannelTxtDao {
    ChannelTxt findById(Integer id);

    ChannelTxt save(ChannelTxt bean);

    ChannelTxt updateByUpdater(Updater<ChannelTxt> updater);
}