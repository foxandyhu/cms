package com.bfly.cms.dao.main;

import com.bfly.cms.entity.main.ChannelCount;
import com.bfly.common.hibernate4.Updater;
import net.sf.ehcache.Ehcache;

public interface ChannelCountDao {
    int clearCount(boolean week, boolean month);

    int clearContentCount(boolean day, boolean week, boolean month, boolean year);

    int freshCacheToDB(Ehcache cache);

    ChannelCount findById(Integer id);

    ChannelCount save(ChannelCount bean);

    ChannelCount updateByUpdater(Updater<ChannelCount> updater);
}