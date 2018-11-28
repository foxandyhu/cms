package com.bfly.cms.funds.service;

import com.bfly.common.page.Pagination;
import com.bfly.cms.user.entity.CmsUser;

import java.util.Date;
import java.util.List;

import com.bfly.cms.funds.entity.CmsAccountDraw;

public interface CmsAccountDrawMng {
	
	 CmsAccountDraw draw(CmsUser user,Double amount,String applyAccount);
	
	 Double getAppliedSum(Integer userId);
	
	 Pagination getPage(Integer userId,Short applyStatus,
			Date applyTimeBegin,Date applyTimeEnd,int pageNo, int pageSize);
	
	 List<CmsAccountDraw> getList(Integer userId,Short applyStatus,
			Date applyTimeBegin,Date applyTimeEnd,Integer first,Integer count);

	 CmsAccountDraw findById(Integer id);

	 CmsAccountDraw save(CmsAccountDraw bean);

	 CmsAccountDraw update(CmsAccountDraw bean);

	 CmsAccountDraw deleteById(Integer id);
	
	 CmsAccountDraw[] deleteByIds(Integer[] ids);
}