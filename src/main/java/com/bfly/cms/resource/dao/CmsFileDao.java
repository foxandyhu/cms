package com.bfly.cms.resource.dao;

import java.util.List;

import com.bfly.cms.resource.entity.CmsFile;
import com.bfly.common.hibernate4.Updater;

public interface CmsFileDao {
    List<CmsFile> getList(Boolean valid);

    CmsFile findById(Integer id);

    CmsFile findByPath(String path);

    CmsFile save(CmsFile bean);

    CmsFile updateByUpdater(Updater<CmsFile> updater);

    CmsFile deleteById(Integer id);

    CmsFile deleteByPath(String path);

    void deleteByContentId(Integer contentId);
}