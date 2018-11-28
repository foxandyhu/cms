package com.bfly.cms.acquisition.service;

import com.bfly.cms.acquisition.entity.CmsAcquisitionShield;
import com.bfly.common.hibernate4.Updater;

import java.util.List;

/**
 * 采集管理批量替换MANGER
 *
 * @author Administrator
 */
public interface CmsAcquisitionShieldMng {

    CmsAcquisitionShield save(CmsAcquisitionShield bean);

    CmsAcquisitionShield updateByUpdater(Updater<CmsAcquisitionShield> updater);

    List<CmsAcquisitionShield> getList(Integer acquisitionId);
}
