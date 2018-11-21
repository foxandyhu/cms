package com.bfly.cms.dao.main;

import com.bfly.cms.entity.main.ContentTxt;
import com.bfly.common.hibernate4.Updater;

public interface ContentTxtDao {
    ContentTxt findById(Integer id);

    ContentTxt save(ContentTxt bean);

    ContentTxt updateByUpdater(Updater<ContentTxt> updater);
}