package com.bfly.cms.dao.main;

import com.bfly.cms.entity.main.ContentCheck;
import com.bfly.common.hibernate4.Updater;

public interface ContentCheckDao {
    ContentCheck findById(Long id);

    ContentCheck save(ContentCheck bean);

    ContentCheck updateByUpdater(Updater<ContentCheck> updater);
}