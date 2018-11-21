package com.bfly.cms.dao.main;

import com.bfly.cms.entity.main.CmsModelItem;
import com.bfly.common.hibernate4.Updater;

import java.util.List;

public interface CmsModelItemDao {
    List<CmsModelItem> getList(Integer modelId, boolean isChannel,
                               Boolean hasDisabled);

    CmsModelItem findById(Integer id);

    CmsModelItem save(CmsModelItem bean);

    CmsModelItem updateByUpdater(Updater<CmsModelItem> updater);

    CmsModelItem deleteById(Integer id);
}