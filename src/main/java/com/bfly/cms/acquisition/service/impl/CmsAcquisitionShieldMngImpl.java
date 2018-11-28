package com.bfly.cms.acquisition.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bfly.cms.acquisition.dao.CmsAcquisitionShieldDao;
import com.bfly.cms.acquisition.entity.CmsAcquisitionShield;
import com.bfly.cms.acquisition.service.CmsAcquisitionShieldMng;
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
