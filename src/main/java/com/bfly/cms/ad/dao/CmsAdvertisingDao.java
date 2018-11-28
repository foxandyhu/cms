package com.bfly.cms.ad.dao;

import com.bfly.cms.ad.entity.CmsAdvertising;
import com.bfly.common.hibernate4.Updater;
import com.bfly.common.page.Pagination;

import java.util.List;

public interface CmsAdvertisingDao {
    Pagination getPage(Integer siteId, Integer adspaceId,
                       Boolean enabled, int pageNo, int pageSize);

    List<CmsAdvertising> getList(Integer adspaceId, Boolean enabled);

    CmsAdvertising findById(Integer id);

    CmsAdvertising save(CmsAdvertising bean);

    CmsAdvertising updateByUpdater(Updater<CmsAdvertising> updater);

    CmsAdvertising deleteById(Integer id);
}