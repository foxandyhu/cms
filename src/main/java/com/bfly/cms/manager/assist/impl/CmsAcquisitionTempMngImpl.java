package com.bfly.cms.manager.assist.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bfly.cms.dao.assist.CmsAcquisitionTempDao;
import com.bfly.cms.entity.assist.CmsAcquisitionTemp;
import com.bfly.cms.manager.assist.CmsAcquisitionTempMng;
import com.bfly.common.hibernate4.Updater;

@Service
@Transactional
public class CmsAcquisitionTempMngImpl implements CmsAcquisitionTempMng {
	@Override
    @Transactional(readOnly = true)
	public List<CmsAcquisitionTemp> getList(Integer siteId) {
		return dao.getList(siteId);
	}

	@Override
    @Transactional(readOnly = true)
	public CmsAcquisitionTemp findById(Integer id) {
		CmsAcquisitionTemp entity = dao.findById(id);
		return entity;
	}

	@Override
    public CmsAcquisitionTemp save(CmsAcquisitionTemp bean) {
		clear(bean.getSite().getId(), bean.getChannelUrl());
		dao.save(bean);
		return bean;
	}

	@Override
    public CmsAcquisitionTemp update(CmsAcquisitionTemp bean) {
		Updater<CmsAcquisitionTemp> updater = new Updater<CmsAcquisitionTemp>(
				bean);
		bean = dao.updateByUpdater(updater);
		return bean;
	}

	@Override
    public CmsAcquisitionTemp deleteById(Integer id) {
		CmsAcquisitionTemp bean = dao.deleteById(id);
		return bean;
	}

	@Override
    public CmsAcquisitionTemp[] deleteByIds(Integer[] ids) {
		CmsAcquisitionTemp[] beans = new CmsAcquisitionTemp[ids.length];
		for (int i = 0, len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}

	@Override
    public Integer getPercent(Integer siteId) {
		return dao.getPercent(siteId);
	}

	@Override
    public void clear(Integer siteId) {
		dao.clear(siteId, null);
	}

	@Override
    public void clear(Integer siteId, String channelUrl) {
		dao.clear(siteId, channelUrl);
	}

	@Autowired
	private CmsAcquisitionTempDao dao;
}