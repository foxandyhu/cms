package com.bfly.cms.dao.assist;

import com.bfly.cms.entity.assist.CmsScoreItem;
import com.bfly.common.hibernate4.Updater;
import com.bfly.common.page.Pagination;

public interface CmsScoreItemDao {
    Pagination getPage(Integer groupId, int pageNo, int pageSize);

    CmsScoreItem findById(Integer id);

    CmsScoreItem save(CmsScoreItem bean);

    CmsScoreItem updateByUpdater(Updater<CmsScoreItem> updater);

    CmsScoreItem deleteById(Integer id);
}