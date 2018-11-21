package com.bfly.cms.dao.assist;

import com.bfly.cms.entity.assist.CmsAcquisitionShield;
import com.bfly.common.hibernate4.Updater;

import java.util.List;

public interface CmsAcquisitionShieldDao {

    CmsAcquisitionShield save(CmsAcquisitionShield bean);

    CmsAcquisitionShield updateByUpdater(Updater<CmsAcquisitionShield> updater);

    List<CmsAcquisitionShield> getList(Integer acquisitionId);
}
