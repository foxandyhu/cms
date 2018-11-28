package com.bfly.cms.job.service;

import com.bfly.common.page.Pagination;

import java.util.List;

import com.bfly.cms.job.entity.CmsJobApply;

public interface CmsJobApplyMng {
	 Pagination getPage(Integer userId,Integer contentId,Integer siteId,
			boolean cacheable,String title,int pageNo, int pageSize);
	
	 List<CmsJobApply> getList(Integer userId,Integer contentId,Integer siteId,
			boolean cacheable,String title,Integer first, Integer count);

	 CmsJobApply findById(Integer id);

	 CmsJobApply save(CmsJobApply bean);

	 CmsJobApply update(CmsJobApply bean);

	 CmsJobApply deleteById(Integer id);
	
	 CmsJobApply[] deleteByIds(Integer[] ids);
}