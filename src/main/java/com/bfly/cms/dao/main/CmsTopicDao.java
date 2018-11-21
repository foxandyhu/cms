package com.bfly.cms.dao.main;

import com.bfly.cms.entity.main.CmsTopic;
import com.bfly.common.hibernate4.Updater;
import com.bfly.common.page.Pagination;

import java.util.List;

public interface CmsTopicDao {
    List<CmsTopic> getList(Integer channelId, boolean recommend,
                           Integer first, Integer count, boolean cacheable);

    Pagination getPage(Integer channelId, String initials, boolean recommend, int pageNo,
                       int pageSize, boolean cacheable);

    List<CmsTopic> getGlobalTopicList();

    List<CmsTopic> getListByChannelId(Integer channelId);

    List<CmsTopic> getListByChannelIds(Integer[] channelIds);

    CmsTopic findById(Integer id);

    CmsTopic save(CmsTopic bean);

    CmsTopic updateByUpdater(Updater<CmsTopic> updater);

    CmsTopic deleteById(Integer id);

    int deleteContentRef(Integer id);

    int countByChannelId(Integer channelId);
}