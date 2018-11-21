package com.bfly.cms.manager.assist.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bfly.cms.dao.assist.CmsMessageDao;
import com.bfly.cms.entity.assist.CmsMessage;
import com.bfly.cms.manager.assist.CmsMessageMng;
import com.bfly.common.hibernate4.Updater;
import com.bfly.common.page.Pagination;

/**
 *江西金磊科技发展有限公司jeecms研发
 */
@Service
@Transactional
public class CmsMessageMngImpl implements CmsMessageMng {

	@Override
    @Transactional(readOnly=true)
	public Pagination getPage(Integer siteId, Integer sendUserId,
			Integer receiverUserId, String title, Date sendBeginTime,
			Date sendEndTime, Boolean status, Integer box, Boolean cacheable,
			int pageNo, int pageSize) {
		return dao.getPage(siteId, sendUserId, receiverUserId, title,
				sendBeginTime, sendEndTime, status, box, cacheable, pageNo,
				pageSize);
	}
	
	@Override
    @Transactional(readOnly=true)
	public List<CmsMessage> getList(Integer siteId, Integer sendUserId,
			Integer receiverUserId, String title, Date sendBeginTime,
			Date sendEndTime, Boolean status, Integer box, Boolean cacheable,
			Integer first, Integer count){
		return dao.getList(siteId, sendUserId, receiverUserId, title,
				sendBeginTime, sendEndTime, status, box, cacheable, first,
				count);
	}

	@Override
    @Transactional(readOnly=true)
	public CmsMessage findById(Integer id) {
		return dao.findById(id);
	}

	@Override
    public CmsMessage save(CmsMessage bean) {
		return dao.save(bean);
	}

	@Override
    public CmsMessage update(CmsMessage bean) {
		Updater<CmsMessage> updater = new Updater<CmsMessage>(bean);
		bean = dao.updateByUpdater(updater);
		return bean;
	}

	@Override
    public CmsMessage deleteById(Integer id) {
		return dao.deleteById(id);
	}

	@Override
    public CmsMessage[] deleteByIds(Integer[] ids) {
		return dao.deleteByIds(ids);
	}

	@Autowired
	private CmsMessageDao dao;

}
