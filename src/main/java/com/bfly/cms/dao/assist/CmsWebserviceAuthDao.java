package com.bfly.cms.dao.assist;

import com.bfly.cms.entity.assist.CmsWebserviceAuth;
import com.bfly.common.hibernate4.Updater;
import com.bfly.common.page.Pagination;

public interface CmsWebserviceAuthDao {
    Pagination getPage(int pageNo, int pageSize);

    CmsWebserviceAuth findByUsername(String username);

    CmsWebserviceAuth findById(Integer id);

    CmsWebserviceAuth save(CmsWebserviceAuth bean);

    CmsWebserviceAuth updateByUpdater(Updater<CmsWebserviceAuth> updater);

    CmsWebserviceAuth deleteById(Integer id);
}