package com.bfly.cms.weixin.service;

import java.util.List;

import com.bfly.common.page.Pagination;
import com.bfly.cms.weixin.entity.WeixinMenu;

public interface WeixinMenuMng {
	
	 Pagination getPage(Integer siteId,Integer parentId
			,int pageNo,int pageSize);
	
	 List<WeixinMenu> getList(Integer siteId,Integer count);
	
	 String getMenuJsonString(Integer siteId);
	
	 WeixinMenu findById(Integer id);
	
	 WeixinMenu save(WeixinMenu bean);
	
	 WeixinMenu update(WeixinMenu bean);
	
	 WeixinMenu deleteById(Integer id);
	
	 WeixinMenu[] deleteByIds(Integer[] ids);
}
