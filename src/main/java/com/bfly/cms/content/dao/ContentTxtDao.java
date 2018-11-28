package com.bfly.cms.content.dao;

import com.bfly.cms.content.entity.ContentTxt;
import com.bfly.common.hibernate4.Updater;

public interface ContentTxtDao {
    ContentTxt findById(Integer id);

    ContentTxt save(ContentTxt bean);

    ContentTxt updateByUpdater(Updater<ContentTxt> updater);
}