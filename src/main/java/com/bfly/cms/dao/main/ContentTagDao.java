package com.bfly.cms.dao.main;

import com.bfly.cms.entity.main.ContentTag;
import com.bfly.common.hibernate4.Updater;
import com.bfly.common.page.Pagination;

import java.util.List;

public interface ContentTagDao {
    List<ContentTag> getList(Integer first, Integer count, boolean cacheable);

    Pagination getPage(String name, int pageNo, int pageSize,
                       boolean cacheable);

    ContentTag findById(Integer id);

    ContentTag findByName(String name, boolean cacheable);

    ContentTag save(ContentTag bean);

    ContentTag updateByUpdater(Updater<ContentTag> updater);

    ContentTag deleteById(Integer id);

    int deleteContentRef(Integer id);

    int countContentRef(Integer id);
}