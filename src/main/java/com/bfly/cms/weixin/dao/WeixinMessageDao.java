package com.bfly.cms.weixin.dao;

import java.util.List;

import com.bfly.common.hibernate4.Updater;
import com.bfly.common.page.Pagination;
import com.bfly.cms.weixin.service.WeixinMessage;

public interface WeixinMessageDao {
	
	 Pagination getPage(Integer siteId,int pageNo,int pageSize);
	
	 List<WeixinMessage> getList(Integer siteId);
	
	 WeixinMessage getWelcome(Integer siteId);
	
	 WeixinMessage findByNumber(String number,Integer siteId);
	
	 WeixinMessage save(WeixinMessage bean);
	
	 WeixinMessage findById(Integer id);
	
	 WeixinMessage deleteById(Integer id);

	 WeixinMessage updateByUpdater(Updater<WeixinMessage> updater);
}
