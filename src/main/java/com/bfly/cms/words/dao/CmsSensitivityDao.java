package com.bfly.cms.words.dao;

import com.bfly.cms.words.entity.CmsSensitivity;
import com.bfly.common.hibernate4.Updater;

import java.util.List;

public interface CmsSensitivityDao {
    List<CmsSensitivity> getList(boolean cacheable);

    CmsSensitivity findById(Integer id);

    CmsSensitivity save(CmsSensitivity bean);

    CmsSensitivity updateByUpdater(Updater<CmsSensitivity> updater);

    CmsSensitivity deleteById(Integer id);
}