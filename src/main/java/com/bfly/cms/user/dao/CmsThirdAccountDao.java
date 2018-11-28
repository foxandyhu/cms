package com.bfly.cms.user.dao;

import com.bfly.cms.user.entity.CmsThirdAccount;
import com.bfly.common.hibernate4.Updater;
import com.bfly.common.page.Pagination;

public interface CmsThirdAccountDao {
    Pagination getPage(String username, String source, int pageNo, int pageSize);

    CmsThirdAccount findById(Long id);

    CmsThirdAccount findByKey(String key);

    CmsThirdAccount save(CmsThirdAccount bean);

    CmsThirdAccount updateByUpdater(Updater<CmsThirdAccount> updater);

    CmsThirdAccount deleteById(Long id);
}