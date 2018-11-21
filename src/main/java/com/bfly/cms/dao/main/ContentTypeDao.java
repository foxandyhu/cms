package com.bfly.cms.dao.main;

import java.util.List;

import com.bfly.cms.entity.main.ContentType;
import com.bfly.common.hibernate4.Updater;

public interface ContentTypeDao {
    List<ContentType> getList(Boolean containDisabled);

    ContentType getDef();

    ContentType findById(Integer id);

    ContentType save(ContentType bean);

    ContentType updateByUpdater(Updater<ContentType> updater);

    ContentType deleteById(Integer id);
}