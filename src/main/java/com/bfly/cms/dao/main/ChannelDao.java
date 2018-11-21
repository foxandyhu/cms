package com.bfly.cms.dao.main;

import com.bfly.cms.entity.main.Channel;
import com.bfly.common.hibernate4.Updater;
import com.bfly.common.page.Pagination;

import java.util.List;

public interface ChannelDao {
    List<Channel> getTopList(Integer siteId, boolean hasContentOnly, boolean displayOnly, boolean cacheable);

    Pagination getTopPage(Integer siteId, boolean hasContentOnly, boolean displayOnly, boolean cacheable, int pageNo, int pageSize);

    List<Channel> getTopListByRigth(Integer userId, Integer siteId, boolean hasContentOnly);

    List<Channel> getChildList(Integer parentId, boolean hasContentOnly, boolean displayOnly, boolean cacheable);

    List<Channel> getBottomList(Integer siteId, boolean hasContentOnly);

    Pagination getChildPage(Integer parentId, boolean hasContentOnly, boolean displayOnly, boolean cacheable, int pageNo, int pageSize);

    List<Channel> getChildListByRight(Integer userId, Integer parentId, boolean hasContentOnly);

    Channel findByPath(String path, Integer siteId, boolean cacheable);

    Channel findById(Integer id);

    Channel save(Channel bean);

    Channel updateByUpdater(Updater<Channel> updater);

    Channel deleteById(Integer id);

    void initWorkFlow(Integer workflowId);
}