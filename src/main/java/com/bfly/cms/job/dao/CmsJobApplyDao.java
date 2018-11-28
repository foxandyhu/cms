package com.bfly.cms.job.dao;

import com.bfly.cms.job.entity.CmsJobApply;
import com.bfly.common.hibernate4.Updater;
import com.bfly.common.page.Pagination;

import java.util.List;

public interface CmsJobApplyDao {
    Pagination getPage(Integer userId, Integer contentId,
                       Integer siteId, boolean cacheable, String title, int pageNo, int pageSize);

    List<CmsJobApply> getList(Integer userId, Integer contentId, Integer siteId,
                              boolean cacheable, String title, Integer first, Integer count);

    CmsJobApply findById(Integer id);

    CmsJobApply save(CmsJobApply bean);

    CmsJobApply updateByUpdater(Updater<CmsJobApply> updater);

    CmsJobApply deleteById(Integer id);
}