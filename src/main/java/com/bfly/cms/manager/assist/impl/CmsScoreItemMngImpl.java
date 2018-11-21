package com.bfly.cms.manager.assist.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bfly.common.hibernate4.Updater;
import com.bfly.common.page.Pagination;
import com.bfly.cms.dao.assist.CmsScoreItemDao;
import com.bfly.cms.entity.assist.CmsScoreItem;
import com.bfly.cms.manager.assist.CmsScoreItemMng;

@Service
@Transactional
public class CmsScoreItemMngImpl implements CmsScoreItemMng {
	@Override
    @Transactional(readOnly = true)
	public Pagination getPage(Integer groupId,int pageNo, int pageSize) {
		Pagination page = dao.getPage(groupId,pageNo, pageSize);
		return page;
	}

	@Override
    @Transactional(readOnly = true)
	public CmsScoreItem findById(Integer id) {
		CmsScoreItem entity = dao.findById(id);
		return entity;
	}

	@Override
    public CmsScoreItem save(CmsScoreItem bean) {
		dao.save(bean);
		return bean;
	}

	@Override
    public CmsScoreItem update(CmsScoreItem bean) {
		Updater<CmsScoreItem> updater = new Updater<CmsScoreItem>(bean);
		bean = dao.updateByUpdater(updater);
		return bean;
	}

	@Override
    public CmsScoreItem deleteById(Integer id) {
		CmsScoreItem bean = dao.deleteById(id);
		return bean;
	}
	
	@Override
    public CmsScoreItem[] deleteByIds(Integer[] ids) {
		CmsScoreItem[] beans = new CmsScoreItem[ids.length];
		for (int i = 0,len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}

	@Autowired
	private CmsScoreItemDao dao;
}