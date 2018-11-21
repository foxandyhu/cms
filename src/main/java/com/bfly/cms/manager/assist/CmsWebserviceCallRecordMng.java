package com.bfly.cms.manager.assist;

import com.bfly.cms.entity.assist.CmsWebserviceCallRecord;
import com.bfly.common.page.Pagination;

public interface CmsWebserviceCallRecordMng {
	public Pagination getPage(int pageNo, int pageSize);

	public CmsWebserviceCallRecord findById(Integer id);
	
	public CmsWebserviceCallRecord save(String clientUsername,String serviceCode);

	public CmsWebserviceCallRecord save(CmsWebserviceCallRecord bean);

	public CmsWebserviceCallRecord update(CmsWebserviceCallRecord bean);

	public CmsWebserviceCallRecord deleteById(Integer id);
	
	public CmsWebserviceCallRecord[] deleteByIds(Integer[] ids);
}