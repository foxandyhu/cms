package com.bfly.cms.dao.assist;

import java.util.List;

import com.bfly.common.hibernate4.Updater;
import com.bfly.common.page.Pagination;
import com.bfly.cms.entity.assist.CmsTask;

public interface CmsTaskDao {
    Pagination getPage(Integer siteId, int pageNo, int pageSize);

    List<CmsTask> getList();

    CmsTask findById(Integer id);

    CmsTask save(CmsTask bean);

    CmsTask updateByUpdater(Updater<CmsTask> updater);

    CmsTask deleteById(Integer id);
}