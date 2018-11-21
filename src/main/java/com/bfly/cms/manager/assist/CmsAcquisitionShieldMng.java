package com.bfly.cms.manager.assist;

import java.util.List;

import com.bfly.cms.entity.assist.CmsAcquisitionShield;
import com.bfly.common.hibernate4.Updater;
/**
 * 采集管理批量替换MANGER
 * @author Administrator
 *
 */
public interface CmsAcquisitionShieldMng {
	
	public CmsAcquisitionShield save(CmsAcquisitionShield bean);
	
	public CmsAcquisitionShield updateByUpdater(Updater<CmsAcquisitionShield> updater);
	
	public List<CmsAcquisitionShield> getList(Integer acquisitionId);
}
