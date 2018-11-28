package com.bfly.cms.user.dao;

import com.bfly.cms.user.entity.CmsUserSite;
import com.bfly.common.hibernate4.Updater;

public interface CmsUserSiteDao {
    CmsUserSite findById(Integer id);

    CmsUserSite save(CmsUserSite bean);

    CmsUserSite updateByUpdater(Updater<CmsUserSite> updater);

    int deleteBySiteId(Integer siteId);

    CmsUserSite deleteById(Integer id);

    void delete(CmsUserSite entity);
}