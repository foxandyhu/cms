package com.bfly.cms.dao.main;

import com.bfly.cms.entity.main.CmsModel;
import com.bfly.common.hibernate4.Updater;

import java.util.List;

public interface CmsModelDao {
    List<CmsModel> getList(boolean containDisabled, Boolean hasContent, Integer siteId);

    CmsModel getDefModel();

    CmsModel findById(Integer id);

    CmsModel findByPath(String path);

    CmsModel save(CmsModel bean);

    CmsModel updateByUpdater(Updater<CmsModel> updater);

    CmsModel deleteById(Integer id);
}