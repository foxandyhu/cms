package com.bfly.cms.manager.main;

import com.bfly.cms.entity.main.Channel;
import com.bfly.cms.entity.main.ChannelExt;

public interface ChannelExtMng {
	public ChannelExt save(ChannelExt ext, Channel channel);

	public ChannelExt update(ChannelExt ext);
}