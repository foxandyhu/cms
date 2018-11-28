package com.bfly.cms.acquisition.service;

import java.util.List;

import com.bfly.cms.acquisition.entity.CmsAcquisitionReplace;
import com.bfly.common.hibernate4.Updater;
/**
 * 采集内容关键词替换业务逻辑接口
 * @author Administrator
 *
 */
public interface CmsAcquisitionReplaceMng {
	
	 CmsAcquisitionReplace save(CmsAcquisitionReplace bean);
	
	 CmsAcquisitionReplace updateByUpdater(Updater<CmsAcquisitionReplace> updater);
	
	 List<CmsAcquisitionReplace>  getList(Integer acquisitionId);
}
