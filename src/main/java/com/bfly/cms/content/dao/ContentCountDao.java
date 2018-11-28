package com.bfly.cms.content.dao;

import com.bfly.cms.content.entity.ContentCount;
import com.bfly.common.hibernate4.Updater;
import net.sf.ehcache.Ehcache;

public interface ContentCountDao {
    int clearCount(boolean week, boolean month);

    int copyCount();

    int freshCacheToDB(Ehcache cache);

    ContentCount findById(Integer id);

    ContentCount save(ContentCount bean);

    ContentCount updateByUpdater(Updater<ContentCount> updater);
}