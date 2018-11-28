package com.bfly.cms.acquisition.service;

import java.util.List;

import com.bfly.cms.acquisition.entity.CmsAcquisitionTemp;

public interface CmsAcquisitionTempMng {
	 List<CmsAcquisitionTemp> getList(Integer siteId);

	 CmsAcquisitionTemp findById(Integer id);

	 CmsAcquisitionTemp save(CmsAcquisitionTemp bean);

	 CmsAcquisitionTemp update(CmsAcquisitionTemp bean);

	 CmsAcquisitionTemp deleteById(Integer id);

	 CmsAcquisitionTemp[] deleteByIds(Integer[] ids);
	
	 Integer getPercent(Integer siteId);
	
	 void clear(Integer siteId);
	
	 void clear(Integer siteId, String channelUrl);
}