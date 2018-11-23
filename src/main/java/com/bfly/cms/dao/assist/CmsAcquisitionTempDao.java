package com.bfly.cms.dao.assist;

import com.bfly.cms.entity.assist.CmsAcquisitionTemp;
import com.bfly.common.hibernate4.Updater;

import java.util.List;

public interface CmsAcquisitionTempDao {
    List<CmsAcquisitionTemp> getList(Integer siteId);

    CmsAcquisitionTemp findById(Integer id);

    CmsAcquisitionTemp save(CmsAcquisitionTemp bean);

    CmsAcquisitionTemp updateByUpdater(Updater<CmsAcquisitionTemp> updater);

    CmsAcquisitionTemp deleteById(Integer id);

    Integer getPercent(Integer siteId);

    void clear(Integer siteId, String channelUrl);
}