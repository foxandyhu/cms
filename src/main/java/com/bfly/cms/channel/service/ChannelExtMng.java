package com.bfly.cms.channel.service;

import com.bfly.cms.channel.entity.Channel;
import com.bfly.cms.channel.entity.ChannelExt;

public interface ChannelExtMng {
    ChannelExt save(ChannelExt ext, Channel channel);

    ChannelExt update(ChannelExt ext);
}