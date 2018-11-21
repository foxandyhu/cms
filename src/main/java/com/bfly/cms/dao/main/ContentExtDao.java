package com.bfly.cms.dao.main;

import com.bfly.cms.entity.main.ContentExt;
import com.bfly.common.hibernate4.Updater;

public interface ContentExtDao {
    ContentExt findById(Integer id);

    ContentExt save(ContentExt bean);

    ContentExt updateByUpdater(Updater<ContentExt> updater);
}