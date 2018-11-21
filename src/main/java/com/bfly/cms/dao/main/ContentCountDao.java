package com.bfly.cms.dao.main;

import com.bfly.cms.entity.main.ContentCount;
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