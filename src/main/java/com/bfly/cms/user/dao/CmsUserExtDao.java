package com.bfly.cms.user.dao;

import com.bfly.cms.user.entity.CmsUserExt;
import com.bfly.common.hibernate4.Updater;

public interface CmsUserExtDao {
    CmsUserExt findById(Integer id);

    CmsUserExt save(CmsUserExt bean);

    CmsUserExt updateByUpdater(Updater<CmsUserExt> updater);

    void clearDayCount();

    int countByPhone(String mobile);
}