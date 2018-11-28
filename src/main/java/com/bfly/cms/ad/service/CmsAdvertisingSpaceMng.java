package com.bfly.cms.ad.service;

import java.util.List;

import com.bfly.cms.ad.entity.CmsAdvertisingSpace;

public interface CmsAdvertisingSpaceMng {
	 List<CmsAdvertisingSpace> getList(Integer siteId);

	 CmsAdvertisingSpace findById(Integer id);

	 CmsAdvertisingSpace save(CmsAdvertisingSpace bean);

	 CmsAdvertisingSpace update(CmsAdvertisingSpace bean);

	 CmsAdvertisingSpace deleteById(Integer id);

	 CmsAdvertisingSpace[] deleteByIds(Integer[] ids);
}