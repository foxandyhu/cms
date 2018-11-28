package com.bfly.cms.acquisition.dao;

import com.bfly.cms.acquisition.entity.CmsAcquisitionShield;
import com.bfly.common.hibernate4.Updater;

import java.util.List;

public interface CmsAcquisitionShieldDao {

    CmsAcquisitionShield save(CmsAcquisitionShield bean);

    CmsAcquisitionShield updateByUpdater(Updater<CmsAcquisitionShield> updater);

    List<CmsAcquisitionShield> getList(Integer acquisitionId);
}
