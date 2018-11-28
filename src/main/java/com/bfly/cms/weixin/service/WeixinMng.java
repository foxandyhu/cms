package com.bfly.cms.weixin.service;

import com.bfly.common.page.Pagination;
import com.bfly.cms.weixin.entity.Weixin;

public interface WeixinMng {

	 Pagination getPage(Integer siteId,int pageNo,int pageSize);
	
	 Weixin findById(Integer id);
	
	 Weixin find(Integer siteId);
	
	 Weixin save(Weixin bean);
	
	 Weixin update(Weixin bean);
	
	 Weixin deleteById(Integer id);
	
	 Weixin[] delete(Integer[] id);
}
