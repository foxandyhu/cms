package com.bfly.cms.user.service;

import com.bfly.common.page.Pagination;
import com.bfly.cms.user.entity.CmsThirdAccount;

public interface CmsThirdAccountMng {
	 Pagination getPage(String username,String source,int pageNo, int pageSize);

	 CmsThirdAccount findById(Long id);
	
	 CmsThirdAccount findByKey(String key);

	 CmsThirdAccount save(CmsThirdAccount bean);

	 CmsThirdAccount update(CmsThirdAccount bean);

	 CmsThirdAccount deleteById(Long id);
	
	 CmsThirdAccount[] deleteByIds(Long[] ids);
}