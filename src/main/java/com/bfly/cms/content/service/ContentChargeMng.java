package com.bfly.cms.content.service;

import java.util.Date;
import java.util.List;

import com.bfly.cms.content.entity.Content;
import com.bfly.cms.content.entity.ContentCharge;
import com.bfly.common.page.Pagination;

public interface ContentChargeMng {
	
	 List<ContentCharge> getList(String contentTitle,Integer authorUserId,
			Date buyTimeBegin,Date buyTimeEnd,int orderBy,Integer first,Integer count);
	
	 Pagination getPage(String contentTitle,Integer authorUserId,
			Date buyTimeBegin,Date buyTimeEnd,
			int orderBy,int pageNo,int pageSize);
	
	 ContentCharge save(Double chargeAmount, Short charge,
			Boolean rewardPattern,Double rewardRandomMin,Double rewardRandomMax,
			Content content);
	
	 void afterContentUpdate(Content bean,Short charge,Double chargeAmount,
			Boolean rewardPattern,Double rewardRandomMin,Double rewardRandomMax);

	 ContentCharge update(ContentCharge charge);
	
	 ContentCharge afterUserPay(Double payAmout, Content content);
}