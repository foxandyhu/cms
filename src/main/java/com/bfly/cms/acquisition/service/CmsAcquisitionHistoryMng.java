package com.bfly.cms.acquisition.service;

import java.util.List;

import com.bfly.cms.acquisition.entity.CmsAcquisitionHistory;
import com.bfly.common.page.Pagination;

public interface CmsAcquisitionHistoryMng {
	 List<CmsAcquisitionHistory> getList(Integer acquId);

	 Pagination getPage(Integer acquId, Integer pageNo,
			Integer pageSize);

	 CmsAcquisitionHistory findById(Integer id);

	 CmsAcquisitionHistory save(CmsAcquisitionHistory bean);

	 CmsAcquisitionHistory update(CmsAcquisitionHistory bean);

	 CmsAcquisitionHistory deleteById(Integer id);

	 CmsAcquisitionHistory[] deleteByIds(Integer[] ids);
	
	 void deleteByAcquisition(Integer acquId);
	
	 Boolean checkExistByProperties(Boolean title, String value);
}