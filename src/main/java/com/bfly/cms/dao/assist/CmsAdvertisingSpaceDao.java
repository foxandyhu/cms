package com.bfly.cms.dao.assist;

import com.bfly.cms.entity.assist.CmsAdvertisingSpace;
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