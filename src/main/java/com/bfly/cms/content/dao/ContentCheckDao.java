package com.bfly.cms.content.dao;

import com.bfly.cms.content.entity.ContentCheck;
import com.bfly.common.hibernate4.Updater;

public interface ContentCheckDao {
    ContentCheck findById(Long id);

    ContentCheck save(ContentCheck bean);

    ContentCheck updateByUpdater(Updater<ContentCheck> updater);
}