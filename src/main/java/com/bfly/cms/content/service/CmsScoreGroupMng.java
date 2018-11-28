package com.bfly.cms.content.service;

import com.bfly.common.page.Pagination;
import com.bfly.cms.content.entity.CmsScoreGroup;

public interface CmsScoreGroupMng {
	 Pagination getPage(int pageNo, int pageSize);

	 CmsScoreGroup findById(Integer id);
	
	 CmsScoreGroup findDefault(Integer siteId);

	 CmsScoreGroup save(CmsScoreGroup bean);

	 CmsScoreGroup update(CmsScoreGroup bean);

	 CmsScoreGroup deleteById(Integer id);
	
	 CmsScoreGroup[] deleteByIds(Integer[] ids);
}