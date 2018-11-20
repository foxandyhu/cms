package com.jeecms.cms.manager.assist.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeecms.cms.dao.assist.CmsAdvertisingSpaceDao;
import com.jeecms.cms.entity.assist.CmsAdvertisingSpace;
import com.jeecms.cms.manager.assist.CmsAdvertisingSpaceMng;
import com.jeecms.common.hibernate4.Updater;

@Service
@Transactional
public class CmsAdvertisingSpaceMngImpl implements CmsAdvertisingSpaceMng {
	@Override
    @Transactional(readOnly = true)
	public List<CmsAdvertisingSpace> getList(Integer siteId) {
		return dao.getList(siteId);
	}

	@Override
    @Transactional(readOnly = true)
	public CmsAdvertisingSpace findById(Integer id) {
		CmsAdvertisingSpace entity = dao.findById(id);
		return entity;
	}

	@Override
    public CmsAdvertisingSpace save(CmsAdvertisingSpace bean) {
		dao.save(bean);
		return bean;
	}

	@Override
    public CmsAdvertisingSpace update(CmsAdvertisingSpace bean) {
		Updater<CmsAdvertisingSpace> updater = new Updater<CmsAdvertisingSpace>(
				bean);
		bean = dao.updateByUpdater(updater);
		return bean;
	}

	@Override
    public CmsAdvertisingSpace deleteById(Integer id) {
		CmsAdvertisingSpace bean = dao.deleteById(id);
		return bean;
	}

	@Override
    public CmsAdvertisingSpace[] deleteByIds(Integer[] ids) {
		CmsAdvertisingSpace[] beans = new CmsAdvertisingSpace[ids.length];
		for (int i = 0, len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}

	@Autowired
	private CmsAdvertisingSpaceDao dao;
}