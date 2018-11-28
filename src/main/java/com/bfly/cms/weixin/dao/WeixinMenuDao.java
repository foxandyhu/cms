package com.bfly.cms.weixin.dao;

import java.util.List;

import com.bfly.common.hibernate4.Updater;
import com.bfly.common.page.Pagination;
import com.bfly.cms.weixin.entity.WeixinMenu;

public interface WeixinMenuDao {
	
	 Pagination getPage(Integer siteId,Integer parentId,int pageNo,int pageSize);
	
	 List<WeixinMenu> getList(Integer siteId,Integer count);
	
	 WeixinMenu findById(Integer id);
	
	 WeixinMenu save(WeixinMenu bean);

	 WeixinMenu updateByUpdater(Updater<WeixinMenu> updater);
	
	 WeixinMenu deleteById(Integer id);
}
