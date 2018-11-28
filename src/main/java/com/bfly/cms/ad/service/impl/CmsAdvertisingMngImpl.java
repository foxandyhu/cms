package com.bfly.cms.ad.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bfly.cms.ad.dao.CmsAdvertisingDao;
import com.bfly.cms.ad.entity.CmsAdvertising;
import com.bfly.cms.ad.service.CmsAdvertisingMng;
import com.bfly.cms.ad.service.CmsAdvertisingSpaceMng;
import com.bfly.common.hibernate4.Updater;
import com.bfly.common.page.Pagination;

@Service
@Transactional
public class CmsAdvertisingMngImpl implements CmsAdvertisingMng {
	@Override
    @Transactional(readOnly = true)
	public Pagination getPage(Integer siteId, Integer adspaceId,
			Boolean enabled, int pageNo, int pageSize) {
		Pagination page = dao.getPage(siteId, adspaceId, enabled, pageNo,
				pageSize);
		return page;
	}

	@Override
    @Transactional(readOnly = true)
	public List<CmsAdvertising> getList(Integer adspaceId, Boolean enabled) {
		return dao.getList(adspaceId, enabled);
	}

	@Override
    @Transactional(readOnly = true)
	public CmsAdvertising findById(Integer id) {
		CmsAdvertising entity = dao.findById(id);
		return entity;
	}

	@Override
    public CmsAdvertising save(CmsAdvertising bean, Integer adspaceId,
                               Map<String, String> attr) {
		bean.setAdspace(cmsAdvertisingSpaceMng.findById(adspaceId));
		bean.setAttr(attr);
		bean.init();
		dao.save(bean);
		return bean;
	}

	@Override
    public CmsAdvertising update(CmsAdvertising bean, Integer adspaceId,
                                 Map<String, String> attr) {
		Updater<CmsAdvertising> updater = new Updater<CmsAdvertising>(bean);
		updater.include(CmsAdvertising.PROP_CODE);
		updater.include(CmsAdvertising.PROP_START_TIME);
		updater.include(CmsAdvertising.PROP_END_TIME);
		bean = dao.updateByUpdater(updater);
		bean.setAdspace(cmsAdvertisingSpaceMng.findById(adspaceId));
		bean.getAttr().clear();
		bean.getAttr().putAll(attr);
		return bean;
	}

	@Override
    public CmsAdvertising deleteById(Integer id) {
		CmsAdvertising bean = dao.deleteById(id);
		return bean;
	}

	@Override
    public CmsAdvertising[] deleteByIds(Integer[] ids) {
		CmsAdvertising[] beans = new CmsAdvertising[ids.length];
		for (int i = 0, len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}

	@Override
    public void display(Integer id) {
		CmsAdvertising ad = findById(id);
		if (ad != null) {
			ad.setDisplayCount(ad.getDisplayCount() + 1);
		}
	}

	@Override
    public void click(Integer id) {
		CmsAdvertising ad = findById(id);
		if (ad != null) {
			ad.setClickCount(ad.getClickCount() + 1);
		}
	}

	@Autowired
	private CmsAdvertisingSpaceMng cmsAdvertisingSpaceMng;
	@Autowired
	private CmsAdvertisingDao dao;

}