package com.bfly.cms.weixin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bfly.common.hibernate4.Updater;
import com.bfly.common.page.Pagination;
import com.bfly.cms.weixin.dao.WeixinMessageDao;
import com.bfly.cms.weixin.service.WeixinMessage;
import com.bfly.cms.weixin.service.WeixinMessageMng;

@Service
@Transactional
public class WeixinMessageMngImpl implements WeixinMessageMng {

	@Override
    @Transactional(readOnly=true)
	public Pagination getPage(Integer siteId,int pageNo, int pageSize) {
		return dao.getPage(siteId,pageNo,pageSize);
	}
	
	@Override
    @Transactional(readOnly=true)
	public List<WeixinMessage> getList(Integer siteId){
		return dao.getList(siteId);
	}
	
	@Override
    @Transactional(readOnly=true)
	public WeixinMessage getWelcome(Integer siteId){
		return dao.getWelcome(siteId);
	}
	
	@Override
    @Transactional(readOnly=true)
	public WeixinMessage findByNumber(String number,Integer siteId){
		return dao.findByNumber(number,siteId);
	}

	@Override
    @Transactional(readOnly=true)
	public WeixinMessage findById(Integer id) {
		return dao.findById(id);
	}

	@Override
	public WeixinMessage save(WeixinMessage bean) {
		return dao.save(bean);
	}

	@Override
	public WeixinMessage update(WeixinMessage bean) {
		Updater<WeixinMessage> updater = new Updater<WeixinMessage>(bean);
		return dao.updateByUpdater(updater);
	}

	@Override
	public WeixinMessage deleteById(Integer id) {
		return dao.deleteById(id);
	}

	@Override
	public WeixinMessage[] deleteByIds(Integer[] ids) {
		WeixinMessage[] beans = new WeixinMessage[ids.length];
		for (int i = 0; i < ids.length; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}
	
	
	@Autowired
	private WeixinMessageDao dao;
}
