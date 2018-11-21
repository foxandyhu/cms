package com.bfly.cms.dao.assist;

import com.bfly.common.hibernate4.Updater;
import com.bfly.common.page.Pagination;
import com.bfly.cms.entity.assist.CmsConfigContentCharge;

public interface CmsConfigContentChargeDao {
    Pagination getPage(int pageNo, int pageSize);

    CmsConfigContentCharge findById(Integer id);

    CmsConfigContentCharge save(CmsConfigContentCharge bean);

    CmsConfigContentCharge updateByUpdater(Updater<CmsConfigContentCharge> updater);

    CmsConfigContentCharge deleteById(Integer id);
}