package com.bfly.cms.dao.assist;

import com.bfly.cms.entity.assist.CmsSearchWords;
import com.bfly.common.hibernate4.Updater;
import com.bfly.common.page.Pagination;

import java.util.List;

public interface CmsSearchWordsDao {
    Pagination getPage(Integer siteId, String name, Integer recommend,
                       Integer orderBy, int pageNo, int pageSize);

    List<CmsSearchWords> getList(Integer siteId, String name,
                                 Integer recommend, Integer orderBy, Integer first,
                                 Integer count, boolean cacheable);

    CmsSearchWords findById(Integer id);

    CmsSearchWords findByName(String name);

    CmsSearchWords save(CmsSearchWords bean);

    CmsSearchWords updateByUpdater(Updater<CmsSearchWords> updater);

    CmsSearchWords deleteById(Integer id);


}