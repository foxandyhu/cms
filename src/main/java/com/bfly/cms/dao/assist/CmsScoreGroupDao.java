package com.bfly.cms.dao.assist;

import com.bfly.cms.entity.assist.CmsScoreGroup;
import com.bfly.common.hibernate4.Updater;
import com.bfly.common.page.Pagination;

public interface CmsScoreGroupDao {
    Pagination getPage(int pageNo, int pageSize);

    CmsScoreGroup findById(Integer id);

    CmsScoreGroup findDefault(Integer siteId);

    CmsScoreGroup save(CmsScoreGroup bean);

    CmsScoreGroup updateByUpdater(Updater<CmsScoreGroup> updater);

    CmsScoreGroup deleteById(Integer id);
}