package com.bfly.cms.manager.assist.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bfly.cms.dao.assist.CmsAcquisitionShieldDao;
import com.bfly.cms.entity.assist.CmsAcquisitionShield;
import com.bfly.cms.manager.assist.CmsAcquisitionShieldMng;
import com.bfly.common.hibernate4.Updater;
@Service
public class CmsAcquisitionShieldMngImpl implements CmsAcquisitionShieldMng {

	@Override
	public CmsAcquisitionShield save(CmsAcquisitionShield bean) {
		return cmsAcquisitionShieldDao.save(bean);
	}

	@Override
	public CmsAcquisitionShield updateByUpdater(Updater<CmsAcquisitionShield> updater) {
		return cmsAcquisitionShieldDao.updateByUpdater(updater);
	}
	
	@Override
	public List<CmsAcquisitionShield> getList(Integer acquisitionId) {		
		return cmsAcquisitionShieldDao.getList(acquisitionId);
	}
	
	@Autowired
	private CmsAcquisitionShieldDao cmsAcquisitionShieldDao;

}
