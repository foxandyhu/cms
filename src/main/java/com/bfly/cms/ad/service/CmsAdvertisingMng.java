package com.bfly.cms.ad.service;

import java.util.List;
import java.util.Map;

import com.bfly.cms.ad.entity.CmsAdvertising;
import com.bfly.common.page.Pagination;

public interface CmsAdvertisingMng {
	 Pagination getPage(Integer siteId, Integer adspaceId,
			Boolean enabled, int pageNo, int pageSize);

	 List<CmsAdvertising> getList(Integer adspaceId, Boolean enabled);

	 CmsAdvertising findById(Integer id);

	 CmsAdvertising save(CmsAdvertising bean, Integer adspaceId,
			Map<String, String> attr);

	 CmsAdvertising update(CmsAdvertising bean, Integer adspaceId,
			Map<String, String> attr);

	 CmsAdvertising deleteById(Integer id);

	 CmsAdvertising[] deleteByIds(Integer[] ids);

	 void display(Integer id);

	 void click(Integer id);
}