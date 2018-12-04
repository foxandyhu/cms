package com.bfly.cms.acquisition.dao;

import com.bfly.cms.acquisition.entity.CmsAcquisitionTemp;
import com.bfly.common.hibernate4.Updater;

import java.util.List;

/**
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/4 10:03
 */
public interface CmsAcquisitionTempDao {
    List<CmsAcquisitionTemp> getList();

    CmsAcquisitionTemp findById(Integer id);

    CmsAcquisitionTemp save(CmsAcquisitionTemp bean);

    CmsAcquisitionTemp updateByUpdater(Updater<CmsAcquisitionTemp> updater);

    CmsAcquisitionTemp deleteById(Integer id);

    Integer getPercent();

    void clear(String channelUrl);
}