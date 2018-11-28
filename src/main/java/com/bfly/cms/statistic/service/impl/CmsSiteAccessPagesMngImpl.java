package com.bfly.cms.statistic.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bfly.cms.statistic.dao.CmsSiteAccessPagesDao;
import com.bfly.cms.statistic.entity.CmsSiteAccessPages;
import com.bfly.cms.statistic.service.CmsSiteAccessPagesMng;
import com.bfly.common.hibernate4.Updater;
import com.bfly.common.page.Pagination;

/**
 * @author Tom
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CmsSiteAccessPagesMngImpl implements CmsSiteAccessPagesMng {

	@Override
    public CmsSiteAccessPages findAccessPage(String sessionId, Integer pageIndex) {
		return dao.findAccessPage(sessionId,pageIndex);
	}
	
	@Override
    public Pagination findPages(Integer siteId, Integer orderBy, Integer pageNo, Integer pageSize){
		return dao.findPages(siteId, orderBy,pageNo,pageSize);
	}

	@Override
    public void clearByDate(Date date) {
		 dao.clearByDate(date);
	}
	
	@Override
    public CmsSiteAccessPages save(CmsSiteAccessPages access) {
		return dao.save(access);
	}
	
	@Override
    public CmsSiteAccessPages update(CmsSiteAccessPages access){
		Updater<CmsSiteAccessPages> updater = new Updater<CmsSiteAccessPages>(access);
		access = dao.updateByUpdater(updater);
		return access;
	}
	
	@Autowired
	private CmsSiteAccessPagesDao dao;

}
