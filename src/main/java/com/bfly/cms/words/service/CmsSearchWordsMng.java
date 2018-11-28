package com.bfly.cms.words.service;

import com.bfly.cms.words.entity.CmsSearchWords;
import com.bfly.common.page.Pagination;
import net.sf.ehcache.Ehcache;

import java.util.List;

public interface CmsSearchWordsMng {
    Pagination getPage(Integer siteId, String name, Integer recommend
            , Integer orderBy, int pageNo, int pageSize);

    List<CmsSearchWords> getList(Integer siteId, String name,
                                 Integer recommend, Integer orderBy,
                                 Integer first, Integer count, boolean cacheable);

    CmsSearchWords findById(Integer id);

    CmsSearchWords save(CmsSearchWords bean);

    CmsSearchWords update(CmsSearchWords bean);

    CmsSearchWords deleteById(Integer id);

    CmsSearchWords[] deleteByIds(Integer[] ids);

    int freshCacheToDB(Ehcache cache);

}