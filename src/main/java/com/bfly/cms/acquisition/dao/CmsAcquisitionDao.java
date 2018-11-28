package com.bfly.cms.acquisition.dao;

import java.util.List;

import com.bfly.cms.acquisition.entity.CmsAcquisition;
import com.bfly.common.hibernate4.Updater;

public interface CmsAcquisitionDao {
    List<CmsAcquisition> getList(Integer siteId);

    CmsAcquisition findById(Integer id);

    CmsAcquisition save(CmsAcquisition bean);

    CmsAcquisition updateByUpdater(Updater<CmsAcquisition> updater);

    CmsAcquisition deleteById(Integer id);

    int countByChannelId(Integer channelId);

    CmsAcquisition getStarted(Integer siteId);

    Integer getMaxQueue(Integer siteId);

    List<CmsAcquisition> getLargerQueues(Integer siteId, Integer queueNum);

    CmsAcquisition popAcquFromQueue(Integer siteId);
}