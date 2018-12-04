package com.bfly.cms.acquisition.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bfly.cms.acquisition.dao.CmsAcquisitionHistoryDao;
import com.bfly.cms.acquisition.entity.CmsAcquisitionHistory;
import com.bfly.cms.acquisition.service.CmsAcquisitionHistoryMng;
import com.bfly.common.hibernate4.Updater;
import com.bfly.common.page.Pagination;

/**
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/4 9:56
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CmsAcquisitionHistoryMngImpl implements CmsAcquisitionHistoryMng {

	@Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
	public List<CmsAcquisitionHistory> getList(Integer acquId) {
		return dao.getList(acquId);
	}
	
	@Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
	public Pagination getPage(Integer acquId, Integer pageNo, Integer pageSize) {
		return dao.getPage(acquId, pageNo, pageSize);
	}

	@Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
	public CmsAcquisitionHistory findById(Integer id) {
		return dao.findById(id);
	}


	@Override
    public CmsAcquisitionHistory save(CmsAcquisitionHistory bean) {
		dao.save(bean);
		return bean;
	}

	@Override
    public CmsAcquisitionHistory update(CmsAcquisitionHistory bean) {
		Updater<CmsAcquisitionHistory> updater = new Updater<>(bean);
		bean = dao.updateByUpdater(updater);
		return bean;
	}

	@Override
    public CmsAcquisitionHistory deleteById(Integer id) {
		return dao.deleteById(id);
	}

	@Override
    public CmsAcquisitionHistory[] deleteByIds(Integer[] ids) {
		CmsAcquisitionHistory[] beans = new CmsAcquisitionHistory[ids.length];
		for (int i = 0, len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}

	@Override
    public void deleteByAcquisition(Integer acquId) {
		 dao.deleteByAcquisition(acquId);
	}
	
	@Override
    public Boolean checkExistByProperties(Boolean title, String value){
		return dao.checkExistByProperties(title, value);
	}

	@Autowired
	private CmsAcquisitionHistoryDao dao;
}