package com.bfly.cms.content.service;

import com.bfly.common.page.Pagination;

import java.util.List;

import com.bfly.cms.content.entity.ContentBuy;

public interface ContentBuyMng {
	
	 ContentBuy contentOrder(Integer contentId,Integer orderType,
			Short chargeReward,Integer buyUserId,String outOrderNum);
	
	 Pagination getPage(String orderNum,Integer buyUserId,Integer authorUserId
			,Short payMode,int pageNo, int pageSize);
	
	 List<ContentBuy> getList(String orderNum,Integer buyUserId,
			Integer authorUserId,Short payMode,Integer first, Integer count);
	
	 Pagination getPageByContent(Integer contentId,
			Short payMode,int pageNo, int pageSize);
	
	 List<ContentBuy> getListByContent(Integer contentId,
			Short payMode,Integer first, Integer count);

	 ContentBuy findById(Long id);
	
	 ContentBuy findByOrderNumber(String orderNumber);
	
	 ContentBuy findByOutOrderNum(String orderNum,Integer payMethod);
	
	 boolean hasBuyContent(Integer buyUserId,Integer contentId);

	 ContentBuy save(ContentBuy bean);

	 ContentBuy update(ContentBuy bean);

	 ContentBuy deleteById(Long id);
	
	 ContentBuy[] deleteByIds(Long[] ids);
}