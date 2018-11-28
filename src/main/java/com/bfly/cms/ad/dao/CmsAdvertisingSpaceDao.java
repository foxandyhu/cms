package com.bfly.cms.ad.dao;

import com.bfly.cms.ad.entity.CmsAdvertisingSpace;
import com.bfly.common.hibernate4.Updater;

import java.util.List;

public interface CmsAdvertisingSpaceDao {
    List<CmsAdvertisingSpace> getList(Integer siteId);

    CmsAdvertisingSpace findById(Integer id);

    CmsAdvertisingSpace save(CmsAdvertisingSpace bean);

    CmsAdvertisingSpace updateByUpdater(
            Updater<CmsAdvertisingSpace> updater);

    CmsAdvertisingSpace deleteById(Integer id);
}