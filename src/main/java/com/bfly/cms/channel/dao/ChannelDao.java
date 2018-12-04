package com.bfly.cms.channel.dao;

import com.bfly.cms.channel.entity.Channel;
import com.bfly.common.hibernate4.Updater;
import com.bfly.common.page.Pagination;

import java.util.List;

public interface ChannelDao {
    List<Channel> getTopList(boolean hasContentOnly, boolean displayOnly, boolean cacheable);

    Pagination getTopPage(boolean hasContentOnly, boolean displayOnly, boolean cacheable, int pageNo, int pageSize);

    List<Channel> getChildList(Integer parentId, boolean hasContentOnly, boolean displayOnly);

    List<Channel> getBottomList(boolean hasContentOnly);

    Pagination getChildPage(Integer parentId, boolean hasContentOnly, boolean displayOnly, int pageNo, int pageSize);

    Channel findByPath(String path, boolean cacheable);

    Channel findById(Integer id);

    Channel save(Channel bean);

    Channel updateByUpdater(Updater<Channel> updater);

    Channel deleteById(Integer id);
}