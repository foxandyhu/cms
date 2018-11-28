package com.bfly.cms.content.dao;

import com.bfly.cms.content.entity.ContentExt;
import com.bfly.common.hibernate4.Updater;

public interface ContentExtDao {
    ContentExt findById(Integer id);

    ContentExt save(ContentExt bean);

    ContentExt updateByUpdater(Updater<ContentExt> updater);
}